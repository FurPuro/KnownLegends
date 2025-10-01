package ru.furpuro.known_legends.blocks

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.DustParticleOptions
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.ScheduledTickAccess
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import ru.furpuro.known_legends.effects.ModMobEffects

class GlitchLaser(props:Properties): GlitchBlock(props) {
    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return Shapes.create(0.05,0.0,0.05,0.95,0.1,0.95)
    }

    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, movedByPiston: Boolean) {
        if (!level.isClientSide) {
            level.scheduleTick(pos,this,1)
        }

        super.onPlace(state, level, pos, oldState, movedByPiston)
    }

    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (!level.isClientSide) {
            var offsetPos:BlockPos = pos
            val effect = MobEffectInstance(ModMobEffects.GLITCH,60,1,false,false,true)
            for (i in 1..10) {
                level.sendParticles(
                    ParticleTypes.ASH,
                    offsetPos.center.x,
                    offsetPos.center.y,
                    offsetPos.center.z,
                    1,
                    0.2,
                    1.0,
                    0.2,
                    0.0
                )
                if (level.getBlockState(offsetPos).isAir) {
                    level.setBlock(offsetPos,ModBlocks.GLITCH_AIR.get().defaultBlockState(),2)
                    level.entities.all.forEach { entity ->
                        if (entity is LivingEntity) {
                            if (entity.blockPosition() == offsetPos) {
                                entity.addEffect(effect)
                            }
                        }
                    }
                } else {
                    break
                }
                offsetPos = offsetPos.above()
            }

            level.scheduleTick(pos,this,1)
        }

        super.tick(state, level, pos, random)
    }

    private fun mayPlaceOn(state: BlockState): Boolean {
        return BuiltInRegistries.BLOCK.getKey(state.block).toString().contains("glitch") && state.isSolidRender
    }

    override fun updateShape(
        state: BlockState,
        level: LevelReader,
        scheduledTickAccess: ScheduledTickAccess,
        pos: BlockPos,
        direction: Direction,
        neighborPos: BlockPos,
        neighborState: BlockState,
        random: RandomSource
    ): BlockState {
        return if (!state.canSurvive(level, pos)
        ) ModBlocks.GLITCH_AIR.get().defaultBlockState()
        else super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random)
    }

    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        val blockpos: BlockPos = pos.below()
        val belowBlockState: BlockState = level.getBlockState(blockpos)
        val soilDecision = belowBlockState.canSustainPlant(level, blockpos, Direction.UP, state)
        if (!soilDecision.isDefault) return soilDecision.isTrue
        return this.mayPlaceOn(belowBlockState)
    }
}