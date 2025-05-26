package ru.furpuro.known_legends.custom

import com.mojang.brigadier.Command
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.neoforged.neoforge.event.RegisterCommandsEvent
import ru.furpuro.known_legends.custom.Functions.spawnErrorBlock

object ErrorBlockSpawnCommand {
    fun register(event: RegisterCommandsEvent) {
        event.dispatcher.register(
            Commands.literal("known_legends")
                .then(
                    Commands.literal("spawn")
                        .then(Commands.literal("error_block")
                            .requires { it.isPlayer }
                            .executes { ctx ->
                                val player = try {
                                    ctx.source.playerOrException
                                } catch (e: Exception) {
                                    ctx.source.sendFailure(Component.literal("Only player can use this command!"))
                                    return@executes 0
                                }

                                val level = player.level() as? ServerLevel
                                if (level == null) {
                                    ctx.source.sendFailure(Component.literal("Can't get world!"))
                                    return@executes 0
                                }

                                spawnErrorBlock(level, player)
                                Command.SINGLE_SUCCESS
                            }
                        )
                )
        )
    }
}