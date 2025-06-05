package ru.furpuro.known_legends.blocks

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.InsideBlockEffectApplier
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
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

    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, movedByPiston: Boolean) {
        if (!level.isClientSide) {
            level.scheduleTick(pos,this,1)
        }

        super.onPlace(state, level, pos, oldState, movedByPiston)
    }

    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (!level.isClientSide) {
            if (!level.getBlockState(pos.below()).isSolidRender) {
                level.destroyBlock(pos,false)
            }
            level.scheduleTick(pos,this,1)
        }

        super.tick(state, level, pos, random)
    }
}