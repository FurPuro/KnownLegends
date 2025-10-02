package ru.furpuro.known_legends.blocks

import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import ru.furpuro.known_legends.blocks.entity.GlitchAltarEntity
import ru.furpuro.known_legends.blocks.entity.ModBlockEntities
import ru.furpuro.known_legends.items.ModItems


class GlitchAltar(props:Properties): BaseEntityBlock(props),EntityBlock {
    private fun mayPlaceOn(state: BlockState): Boolean {
        return BuiltInRegistries.BLOCK.getKey(state.block).toString().contains("glitch") && state.isSolidRender
    }

    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        val blockpos: BlockPos = pos.below()
        val belowBlockState: BlockState = level.getBlockState(blockpos)
        val soilDecision = belowBlockState.canSustainPlant(level, blockpos, Direction.UP, state)
        if (!soilDecision.isDefault) return soilDecision.isTrue
        return this.mayPlaceOn(belowBlockState)
    }

    override fun newBlockEntity(p0: BlockPos, p1: BlockState): BlockEntity? {
        return ModBlockEntities.GLITCH_ALTAR.get().create(p0,p1)
    }

    override fun codec(): MapCodec<out BaseEntityBlock> {
        TODO("Not yet implemented")
    }

    override fun propagatesSkylightDown(state: BlockState): Boolean = true
    override fun getLightBlock(state: BlockState): Int = 1

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return Block.box(1.0,0.0,1.0,15.0,11.0,15.0)
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
        if (level.getBlockEntity(pos) is GlitchAltarEntity) {
            val be = level.getBlockEntity(pos) as GlitchAltarEntity
            if (be.inventory.getStackInSlot(0).isEmpty && !stack.isEmpty) {
                be.inventory.insertItem(0, stack.copy(), false)
                stack.shrink(1)
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f)
            } else if (stack.isEmpty) {
                val stackOnPedestal: ItemStack = be.inventory.extractItem(0, 1, false)
                player.setItemInHand(InteractionHand.MAIN_HAND, stackOnPedestal)
                be.clearContents()
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f)
            }
        }

        return InteractionResult.SUCCESS
    }

    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, movedByPiston: Boolean) {
        level.scheduleTick(pos,asBlock(),2)

        super.onPlace(state, level, pos, oldState, movedByPiston)
    }

    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (level.getBlockEntity(pos) is GlitchAltarEntity) {
            val be = level.getBlockEntity(pos) as GlitchAltarEntity

            if (be.inventory.getStackInSlot(0).`is`(Items.DIAMOND_SWORD) && be.progress < 1.00f) {
                be.progress += 0.01f
                be.rotationSpeed += be.progress*10f
                level.sendParticles(
                    ParticleTypes.GLOW,
                    pos.x+0.5,
                    pos.y+0.7,
                    pos.z+0.5,
                    1,
                    0.1,
                    0.1,
                    0.1,
                    be.progress.toDouble()
                )
            } else if (be.progress > 0.01f) {
                be.progress = 0f
                be.rotationSpeed = be.defaultRotationSpeed
            }

            if (be.progress >= 1.00f) {
                be.rotationSpeed = be.defaultRotationSpeed
                be.progress = 0f
                be.inventory.extractItem(0,1,false)
                be.inventory.insertItem(0,ModItems.GLITCH_SWORD.toStack(1),false)
            }
        }

        level.scheduleTick(pos,asBlock(),2)

        super.tick(state, level, pos, random)
    }
}