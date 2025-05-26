package ru.furpuro.known_legends.custom

import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import ru.furpuro.known_legends.block.ModBlocks

object Functions {
    fun spawnErrorBlock(level: ServerLevel, player: ServerPlayer) {
        val pos = player.blockPosition()
        val attempts = 20
        var foundPos: BlockPos? = null
        for (i in 1..attempts) {
            val candidate = pos.offset(
                level.random.nextInt(-100, 101).coerceIn(level.worldBorder.minX.toInt(), level.worldBorder.maxX.toInt()),
                level.random.nextInt(-50, 51).coerceIn(level.minY, level.maxY),
                level.random.nextInt(-100, 101).coerceIn(level.worldBorder.minZ.toInt(), level.worldBorder.maxZ.toInt())
            )

            val targetState = level.getBlockState(candidate)

            if (targetState.isSolidRender && !targetState.isAir) {
                foundPos = candidate
                break
            }
        }
        if (foundPos != null) {
            level.setBlock(foundPos, ModBlocks.ERROR_BLOCK.get().defaultBlockState(), 1)
            level.players().forEach { p ->
                p.sendSystemMessage(Component.literal("Something broke..."))
            }
            println(foundPos)
        }
    }
}