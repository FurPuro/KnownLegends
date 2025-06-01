package ru.furpuro.known_legends.blocks

import net.minecraft.commands.arguments.CompoundTagArgument
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.redstone.Orientation
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.ticks.TickPriority
import ru.furpuro.known_legends.items.ModItems

class FixGasSprayer(props: Properties) : Block(props) {
    companion object {
        val POWERED: BooleanProperty = BlockStateProperties.POWERED
    }

    init {
        registerDefaultState(this.stateDefinition.any().setValue(POWERED, false))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(POWERED)
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
            val be: FixGasSprayerEntity? = ModBlockEntities.FIX_GAS_SPRAYER.get().getBlockEntity(level, pos)
            if (be != null) {
                if (stack.item.asItem() == ModItems.FIX_POWDER.get()) {
                    repeat(stack.count) {
                        if (be.GAS < be.MAX_GAS) {
                            if (!player.isCreative) {
                                stack.shrink(1)
                            }
                            be.GAS += 500
                            be.GAS.coerceIn(0,be.MAX_GAS)
                        }
                    }
                }
            } else {
                println("Can't get entity")
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
            val be: FixGasSprayerEntity? = ModBlockEntities.FIX_GAS_SPRAYER.get().getBlockEntity(level, pos)
            if (be != null) {
                println(be.blockPos)
                player.displayClientMessage(Component.literal("Gas: ${be.GAS}"),true)
                be.GAS.coerceIn(0,be.MAX_GAS)
            } else {
                println("Can't get entity")
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
        val be: FixGasSprayerEntity? = ModBlockEntities.FIX_GAS_SPRAYER.get().getBlockEntity(level, pos)
        if (be != null) {
            println(be.blockPos)
            val airBlock = ModBlocks.FIX_GAS.get()
            for (dx in -1..1) for (dy in -1..1) for (dz in -1..1) {
                val target = pos.offset(dx, dy, dz)
                if (level.getBlockState(target).isAir && be.GAS >= be.GAS_CONSUME) {
                    level.setBlock(target, airBlock.defaultBlockState(), 3)
                    be.GAS -= be.GAS_CONSUME
                    be.GAS.coerceIn(0,be.MAX_GAS)
                }
            }
        } else {
            println("Can't get entity")
        }
    }

    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, movedByPiston: Boolean) {
        if (!level.isClientSide()) {
            val be = ModBlockEntities.FIX_GAS_SPRAYER.get().create(pos,defaultBlockState())
                //ModBlockEntities.FIX_GAS_SPRAYER.get().create(pos, state).persistentData
                //    .let { BlockEntity.loadStatic(pos,defaultBlockState(), it,level.registryAccess()) }
            if (be != null) {
                println("Entity is removed: ${be.isRemoved}; valid BlockState: ${be.isValidBlockState(defaultBlockState())}")
                println("Successfully created ${be.type} entity at $pos")
            } else {
                println("Can't create entity at $pos")
            }
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