package ru.furpuro.known_legends.blocks

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.InsideBlockEffectApplier
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.ScheduledTickAccess
import net.minecraft.world.level.block.state.BlockState
import ru.furpuro.known_legends.effects.ModMobEffects

class GlitchGrassBlock(props: Properties) : GlitchBlock(props.randomTicks()) {
    override fun propagatesSkylightDown(state: BlockState): Boolean = true
    override fun getLightBlock(state: BlockState): Int = 1

    override fun entityInside(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        entity: Entity,
        effectApplier: InsideBlockEffectApplier
    ) {
        if (!level.isClientSide) {
            if (entity is LivingEntity) {
                val effect = MobEffectInstance(ModMobEffects.GLITCH,200,0,false,false,true)
                entity.addEffect(effect)
            }
        }

        super.entityInside(state, level, pos, entity, effectApplier)
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