package ru.furpuro.known_legends.entities

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EntityType.EntityFactory
import net.minecraft.world.entity.MobCategory
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.DeferredRegister.Entities
import ru.furpuro.known_legends.Known_legends.ID
import ru.furpuro.known_legends.entities.glitch_entity.GlitchEntity


object ModEntityTypes {
    val ENTITY_TYPES: Entities = DeferredRegister.createEntities(ID)

    val GLITCH_ENTITY: DeferredHolder<EntityType<*>, EntityType<GlitchEntity>> = ENTITY_TYPES.registerEntityType("glitch_entity", EntityFactory(::GlitchEntity),MobCategory.MONSTER) { builder ->
        builder
            .eyeHeight(1.4f)
            .sized(0.8f,1.6f)
    }
}