package ru.furpuro.known_legends.entities

import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.monster.Monster
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent

class GlitchEntityAttributes {
    @SubscribeEvent
    fun createDefaultAttributes(event: EntityAttributeCreationEvent) {
        event.put(
            ModEntityTypes.GLITCH_ENTITY.get(),
            Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 18.0)
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.27)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.ARMOR, 8.0)
                .build()
        )
    }
}