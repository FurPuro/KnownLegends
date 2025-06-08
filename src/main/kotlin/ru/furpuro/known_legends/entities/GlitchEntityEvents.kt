package ru.furpuro.known_legends.entities

import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.monster.Monster.createLivingAttributes
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent

class GlitchEntityEvents {

    @SubscribeEvent
    fun createDefaultAttributes(event: EntityAttributeCreationEvent) {
        event.put(
            ModEntityTypes.GLITCH_ENTITY.get(),
            createLivingAttributes()
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.23)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.ARMOR, 2.0)
                .build()
        )
    }
}