package ru.furpuro.known_legends.entities

import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState
import software.bernie.geckolib.renderer.GeoEntityRenderer
import software.bernie.geckolib.renderer.base.GeoRenderState


class GlitchEntityRenderer<R>(context: EntityRendererProvider.Context) :
    GeoEntityRenderer<GlitchEntity, R>(context, GlitchEntityModel())
        where R : LivingEntityRenderState, R : GeoRenderState