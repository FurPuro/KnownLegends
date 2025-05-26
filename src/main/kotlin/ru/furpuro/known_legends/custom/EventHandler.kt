package ru.furpuro.known_legends.custom

import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.RegisterCommandsEvent
import net.neoforged.neoforge.event.tick.LevelTickEvent
import ru.furpuro.known_legends.block.ModBlocks
import ru.furpuro.known_legends.custom.Functions.spawnErrorBlock


class EventHandler {
    @SubscribeEvent
    fun onRegisterCommands(event: RegisterCommandsEvent) {
        ErrorBlockSpawnCommand.register(event)
    }

    @SubscribeEvent
    fun onWorldTick(event: LevelTickEvent.Post) {
        if (!event.level.isClientSide) {
            val level = event.level as ServerLevel
            val player = level.players().randomOrNull()

            if (player != null) {
                if (level.getRandom().nextInt(50000) == 0) {
                    spawnErrorBlock(level, player)
                }
            }
        }
    }
}