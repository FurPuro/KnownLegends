package ru.furpuro.known_legends.effects

import net.minecraft.core.registries.Registries
import net.minecraft.world.effect.MobEffect
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import ru.furpuro.known_legends.Known_legends

object ModMobEffects {
    val REGISTRY: DeferredRegister<MobEffect> = DeferredRegister.create(Registries.MOB_EFFECT,Known_legends.ID)

    val GLITCH: DeferredHolder<MobEffect, GlitchMobEffect> = REGISTRY.register("glitch") { ->
        GlitchMobEffect()
    }
}