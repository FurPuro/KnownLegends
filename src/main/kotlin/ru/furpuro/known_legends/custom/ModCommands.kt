package ru.furpuro.known_legends.custom

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.IntegerArgumentType
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.neoforged.neoforge.event.RegisterCommandsEvent
import ru.furpuro.known_legends.custom.Functions.getPhase
import ru.furpuro.known_legends.custom.Functions.spawnGlitchAir
import ru.furpuro.known_legends.data.ModAttachments

object ModCommands {
    fun register(event: RegisterCommandsEvent) {
        event.dispatcher.register(
            Commands.literal("known_legends")
                .then(
                    Commands.literal("spawn")
                        .then(Commands.literal("glitch_air")
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

                                spawnGlitchAir(level, player)
                                Command.SINGLE_SUCCESS
                            }
                        )
                )
                .then(
                    Commands.literal("phase")
                        .then(
                            Commands.literal("get")
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

                                    val data = level.getData(ModAttachments.POINTS_DATA)

                                    ctx.source.sendSystemMessage(Component.literal("Current phase is: ${getPhase(data.points)} (${data.points} points)"))
                                    Command.SINGLE_SUCCESS
                                }
                        ).then(
                            Commands.literal("add")
                                .requires { it.isPlayer }
                                .then(
                                    Commands.argument("number",IntegerArgumentType.integer())
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

                                            val count = IntegerArgumentType.getInteger(ctx, "number")
                                            val data = level.getData(ModAttachments.POINTS_DATA)
                                            data.points = count
                                            ctx.source.sendSystemMessage(Component.literal("Now points is: ${data.points}"))
                                            Command.SINGLE_SUCCESS
                                        }
                                )

                        )
                )
        )
    }
}