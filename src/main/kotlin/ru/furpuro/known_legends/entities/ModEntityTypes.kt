package ru.furpuro.known_legends.entities

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EntityType.EntityFactory
import net.minecraft.world.entity.MobCategory
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.DeferredRegister.Entities
import ru.furpuro.known_legends.Known_legends.ID
import ru.furpuro.known_legends.entities.glitch_crawler.GlitchCrawler
import ru.furpuro.known_legends.entities.glitch_entity.GlitchEntity
import ru.furpuro.known_legends.entities.glitch_parasite.GlitchParasite


object ModEntityTypes {
    val ENTITY_TYPES: Entities = DeferredRegister.createEntities(ID)

    val GLITCH_ENTITY: DeferredHolder<EntityType<*>, EntityType<GlitchEntity>> = ENTITY_TYPES.registerEntityType("glitch_entity", EntityFactory(::GlitchEntity),MobCategory.MONSTER) { builder ->
        builder
            .eyeHeight(1.4f)
            .sized(0.8f,1.6f)
    }
    val GLITCH_PARASITE: DeferredHolder<EntityType<*>, EntityType<GlitchParasite>> = ENTITY_TYPES.registerEntityType("glitch_parasite", EntityFactory(::GlitchParasite),MobCategory.MONSTER) { builder ->
        builder
            .eyeHeight(0.125f)
            .sized(0.5f,0.25f)
    }
    val GLITCH_CRAWLER: DeferredHolder<EntityType<*>, EntityType<GlitchCrawler>> = ENTITY_TYPES.registerEntityType("glitch_crawler", EntityFactory(::GlitchCrawler),MobCategory.MONSTER) { builder ->
        builder
            .eyeHeight(0.4f)
            .sized(0.75f,0.55f)
    }
}