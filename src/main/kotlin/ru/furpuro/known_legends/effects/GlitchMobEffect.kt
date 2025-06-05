package ru.furpuro.known_legends.effects

import net.minecraft.core.particles.ParticleTypes
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import ru.furpuro.known_legends.custom.Functions.getPhase
import ru.furpuro.known_legends.custom.Functions.placeGlitch
import ru.furpuro.known_legends.data.ModAttachments
import kotlin.math.round

class GlitchMobEffect : MobEffect(
    MobEffectCategory.HARMFUL,
    0x2B0042
) {
    override fun applyEffectTick(level: ServerLevel, entity: LivingEntity, amplifier: Int): Boolean {
        if (!entity.level().isClientSide) {
            entity.hurtServer(level, entity.damageSources().inWall(),1f*(amplifier+1))

            val targetState = level.getBlockState(entity.blockPosition().below())

            val pos = entity.blockPosition().below()

            val blockId = BuiltInRegistries.BLOCK.getKey(targetState.block).toString()

            val data = level.getData(ModAttachments.POINTS_DATA)
            val phase = getPhase(data.points)

            if (!targetState.isAir) {
                placeGlitch(blockId,phase,level.random,targetState,level,pos,pos)
            }

            spawnDamageParticles(entity)
        }

        return true
    }

    private fun spawnDamageParticles(entity: LivingEntity) {
        val level = entity.level() as? ServerLevel ?: return
        val pos = entity.position()

        level.sendParticles(
            ParticleTypes.DRAGON_BREATH,
            pos.x, pos.y + entity.eyeHeight/2, pos.z,
            5, 0.2, 0.5, 0.2, 0.05
        )
    }

    override fun shouldApplyEffectTickThisTick(duration: Int, amplifier: Int): Boolean {
        return (duration%round((10/(amplifier+1)).toDouble())).toInt()==0
    }
}