package ru.furpuro.known_legends.blocks

import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.redstone.Orientation
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.ticks.TickPriority
import ru.furpuro.known_legends.blocks.entity.FixGasSprayerEntity
import ru.furpuro.known_legends.blocks.entity.ModBlockEntities
import ru.furpuro.known_legends.items.ModItems

class FixGasSprayer(properties:Properties) : BaseEntityBlock(properties) {
    val CODEC: MapCodec<FixGasSprayer> = simpleCodec(::FixGasSprayer)

    companion object {
        val POWERED: BooleanProperty = BlockStateProperties.POWERED
    }

    init {
        registerDefaultState(this.stateDefinition.any().setValue(POWERED, false))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(POWERED)
    }

    override fun codec(): MapCodec<out BaseEntityBlock> {
        return CODEC
    }

    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.MODEL
    }

    override fun newBlockEntity(p0: BlockPos, p1: BlockState): BlockEntity {
        return FixGasSprayerEntity(p0,p1)
    }

    override fun useItemOn(
        stack: ItemStack,
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hitResult: BlockHitResult
    ): InteractionResult {
        if (!level.isClientSide()) {
            if (level.getBlockEntity(pos) is FixGasSprayerEntity) {
                val be = level.getBlockEntity(pos) as FixGasSprayerEntity
                if (stack.item.asItem() == ModItems.FIX_POWDER.get()) {
                    repeat(stack.count) {
                        if (be.gas < be.maxGas) {
                            if (!player.isCreative) {
                                stack.shrink(1)
                            }
                            be.gas += 5000
                            be.gas = be.gas.coerceIn(0,be.maxGas)
                        }
                    }
                }
            }
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult)
    }

    override fun useWithoutItem(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hitResult: BlockHitResult
    ): InteractionResult {
        if (!level.isClientSide()) {
            if (level.getBlockEntity(pos) is FixGasSprayerEntity) {
                val be = level.getBlockEntity(pos) as FixGasSprayerEntity
                player.displayClientMessage(Component.literal("${be.gas}"),true)
                be.gas = be.gas.coerceIn(0,be.maxGas)
            }
        }

        return super.useWithoutItem(state, level, pos, player, hitResult)
    }

    override fun neighborChanged(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        block: Block,
        orientation: Orientation?,
        isMoving: Boolean
    ) {
        if (!level.isClientSide) {
            val powered = level.hasNeighborSignal(pos)
            if (powered && !state.getValue(POWERED)) {
                level.setBlock(pos, state.setValue(POWERED, true), 2)
            } else if (!powered && state.getValue(POWERED)) {
                level.setBlock(pos, state.setValue(POWERED, false), 2)
            }
        }
        super.neighborChanged(state, level, pos, block, orientation, isMoving)
    }

    private fun spreadAir(level: Level, pos: BlockPos) {
        if (level.getBlockEntity(pos) is FixGasSprayerEntity) {
            val be = level.getBlockEntity(pos) as FixGasSprayerEntity
            val airBlock = ModBlocks.FIX_GAS.get()
            for (dx in -1..1) for (dy in -1..1) for (dz in -1..1) {
                val target = pos.offset(dx, dy, dz)
                if (level.getBlockState(target).isAir && be.gas >= be.gasConsume) {
                    level.setBlock(target, airBlock.defaultBlockState(), 3)
                    be.gas -= be.gasConsume
                    be.gas = be.gas.coerceIn(0,be.maxGas)
                }
            }
        }
    }

    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, movedByPiston: Boolean) {
        if (!level.isClientSide()) {
            level.scheduleTick(pos, this, 15, TickPriority.NORMAL)
        }
        super.onPlace(state, level, pos, oldState, movedByPiston)
    }

    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (!level.isClientSide && state.getValue(POWERED)) {
            spreadAir(level, pos)
            level.scheduleTick(pos, this, 15, TickPriority.NORMAL)
        }
        super.tick(state, level, pos, random)
    }
}