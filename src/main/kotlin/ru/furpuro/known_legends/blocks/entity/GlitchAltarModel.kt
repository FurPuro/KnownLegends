package ru.furpuro.known_legends.blocks.entity

import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import ru.furpuro.known_legends.Known_legends
import software.bernie.geckolib.model.DefaultedBlockGeoModel
import software.bernie.geckolib.renderer.base.GeoRenderState

class GlitchAltarModel :
    DefaultedBlockGeoModel<GlitchAltarEntity?>(
        ResourceLocation.fromNamespaceAndPath(
            Known_legends.ID,
            "glitch_altar"
        )
    )