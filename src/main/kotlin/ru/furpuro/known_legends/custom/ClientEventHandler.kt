package ru.furpuro.known_legends.custom

import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.client.event.EntityRenderersEvent
import ru.furpuro.known_legends.blocks.entity.GlitchAltarRenderer
import ru.furpuro.known_legends.blocks.entity.ModBlockEntities
import ru.furpuro.known_legends.entities.ModEntityTypes
import ru.furpuro.known_legends.entities.ModLivingEntityRenderState
import ru.furpuro.known_legends.entities.glitch_crawler.GlitchCrawlerRenderer
import ru.furpuro.known_legends.entities.glitch_entity.GlitchEntityRenderer
import ru.furpuro.known_legends.entities.glitch_parasite.GlitchParasiteRenderer

class ClientEventHandler {
    @SubscribeEvent
    fun registerEntityRenderers(event: EntityRenderersEvent.RegisterRenderers) {
        event.registerEntityRenderer(ModEntityTypes.GLITCH_ENTITY.get()) { context ->
            GlitchEntityRenderer<ModLivingEntityRenderState>(context)
        }
        event.registerEntityRenderer(ModEntityTypes.GLITCH_PARASITE.get()) { context ->
            GlitchParasiteRenderer<ModLivingEntityRenderState>(context)
        }
        event.registerEntityRenderer(ModEntityTypes.GLITCH_CRAWLER.get()) { context ->
            GlitchCrawlerRenderer<ModLivingEntityRenderState>(context)
        }

        event.registerBlockEntityRenderer(ModBlockEntities.GLITCH_ALTAR.get()) {
            GlitchAltarRenderer()
        }
    }
}