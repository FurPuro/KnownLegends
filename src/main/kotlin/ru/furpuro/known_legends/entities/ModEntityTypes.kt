package ru.furpuro.known_legends.entities

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EntityType.EntityFactory
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.DeferredRegister.Entities
import ru.furpuro.known_legends.Known_legends
import java.util.function.Supplier
import java.util.function.UnaryOperator


object ModEntityTypes {
    val ENTITY_TYPES: Entities = DeferredRegister.createEntities(Known_legends.ID)

    val GLITCH_ENTITY: Supplier<EntityType<GlitchEntity>> = ENTITY_TYPES.register(
        "glitch_entity",  // The entity type, created using a builder.
        Supplier {
            EntityType.Builder.of(
                ::GlitchEntity,
                MobCategory.MONSTER
            )
                .sized(0.75f, 0.8f)
                .eyeHeight(0.5f)
                .clientTrackingRange(8)
                .updateInterval(10)
                .build(
                    ResourceKey.create(
                        Registries.ENTITY_TYPE,
                        ResourceLocation.fromNamespaceAndPath(Known_legends.ID, "glitch_entity")
                    )
                )
        }
    )
}