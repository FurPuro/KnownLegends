package ru.furpuro.known_legends.entities.glitch_parasite

import net.minecraft.resources.ResourceLocation
import ru.furpuro.known_legends.Known_legends
import software.bernie.geckolib.model.DefaultedEntityGeoModel


class GlitchParasiteModel :
    DefaultedEntityGeoModel<GlitchParasite?>(ResourceLocation.fromNamespaceAndPath(Known_legends.ID, "glitch_parasite"))