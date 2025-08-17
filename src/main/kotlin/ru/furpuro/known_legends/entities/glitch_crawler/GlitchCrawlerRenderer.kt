package ru.furpuro.known_legends.entities.glitch_crawler

import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState
import software.bernie.geckolib.renderer.GeoEntityRenderer
import software.bernie.geckolib.renderer.base.GeoRenderState


class GlitchCrawlerRenderer<R>(context: EntityRendererProvider.Context) :
    GeoEntityRenderer<GlitchCrawler, R>(context, GlitchCrawlerModel())
        where R : LivingEntityRenderState, R : GeoRenderState