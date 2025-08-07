package ru.furpuro.known_legends.custom

import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.client.event.EntityRenderersEvent
import ru.furpuro.known_legends.entities.GlitchEntityRenderer
import ru.furpuro.known_legends.entities.ModEntityTypes
import ru.furpuro.known_legends.entities.RenderState

class ClientEventHandler {
    @SubscribeEvent
    fun registerEntityRenderers(event: EntityRenderersEvent.RegisterRenderers) {
        event.registerEntityRenderer(ModEntityTypes.GLITCH_ENTITY.get()) { context ->
            GlitchEntityRenderer<RenderState>(context)
        }
    }
}