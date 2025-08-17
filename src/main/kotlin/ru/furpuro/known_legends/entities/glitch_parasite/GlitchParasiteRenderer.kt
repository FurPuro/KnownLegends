package ru.furpuro.known_legends.entities.glitch_parasite

import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState
import software.bernie.geckolib.renderer.GeoEntityRenderer
import software.bernie.geckolib.renderer.base.GeoRenderState


class GlitchParasiteRenderer<R>(context: EntityRendererProvider.Context) :
    GeoEntityRenderer<GlitchParasite, R>(context, GlitchParasiteModel())
        where R : LivingEntityRenderState, R : GeoRenderState