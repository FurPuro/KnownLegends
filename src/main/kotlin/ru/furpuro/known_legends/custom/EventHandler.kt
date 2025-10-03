package ru.furpuro.known_legends.custom

import com.mojang.blaze3d.shaders.FogShape
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.FogRenderer
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.client.event.ViewportEvent
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeFogColor
import net.neoforged.neoforge.event.RegisterCommandsEvent
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent
import net.neoforged.neoforge.event.tick.LevelTickEvent
import ru.furpuro.known_legends.blocks.ModBlocks
import ru.furpuro.known_legends.blocks.entity.GlitchAltarEntity
import ru.furpuro.known_legends.custom.Functions.glitchRemoveFunction
import ru.furpuro.known_legends.custom.Functions.spawnGlitchAir


class EventHandler {
    @SubscribeEvent
    fun onRegisterCommands(event: RegisterCommandsEvent) {
        ModCommands.register(event)
    }

    @SubscribeEvent
    fun onWorldTick(event: LevelTickEvent.Post) {
        if (!event.level.isClientSide) {
            val level = event.level as ServerLevel
            val player = level.players().randomOrNull()

            if (player != null) {
                if (level.getRandom().nextInt(40000) == 0) {
                    spawnGlitchAir(level, player)
                }
            }
        }
    }

    @SubscribeEvent
    fun onComputeFogColor(event: ComputeFogColor) {
        val player = Minecraft.getInstance().player ?: return
        val world = player.level()
        val pos = player.blockPosition()
        val block = world.getBlockState(pos).block

        if (block == ModBlocks.GLITCH_AIR.get()) {
            event.red = 0.18f
            event.green = 0.07f
            event.blue = 0.3f
        }
    }

    @SubscribeEvent
    fun onRenderFog(event: ViewportEvent.RenderFog) {
        val player = Minecraft.getInstance().player ?: return
        val world = player.level()
        val pos = player.blockPosition()
        val block = world.getBlockState(pos).block

        if (block == ModBlocks.GLITCH_AIR.get() && (event.mode == FogRenderer.FogMode.FOG_TERRAIN || event.mode == FogRenderer.FogMode.FOG_SKY)) {
            event.nearPlaneDistance = 0.05f
            event.farPlaneDistance = 0.2f
            event.fogShape = FogShape.SPHERE
        }
    }

    @SubscribeEvent
    fun onBlockBreak(event: BreakEvent) {
        val pos = event.pos
        val state = event.state
        val level = event.level
        val blockId = BuiltInRegistries.BLOCK.getKey(state.block).toString()
        if (!level.isClientSide && level is Level) {
            if (blockId.contains("glitch") && !blockId.contains("decor")) {
                glitchRemoveFunction(level)
            } else if (level.getBlockEntity(pos) is GlitchAltarEntity) {
                var be = level.getBlockEntity(pos) as GlitchAltarEntity
                be.drops()
            }
        }
    }
}