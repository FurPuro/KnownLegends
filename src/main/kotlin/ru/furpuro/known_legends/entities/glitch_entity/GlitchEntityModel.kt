package ru.furpuro.known_legends.entities.glitch_entity

import net.minecraft.resources.ResourceLocation
import ru.furpuro.known_legends.Known_legends
import software.bernie.geckolib.model.DefaultedEntityGeoModel


class GlitchEntityModel :
    DefaultedEntityGeoModel<GlitchEntity?>(ResourceLocation.fromNamespaceAndPath(Known_legends.ID, "glitch_entity"))