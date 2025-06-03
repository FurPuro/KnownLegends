package ru.furpuro.known_legends.custom

import net.minecraft.core.BlockPos
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.tags.BlockTags
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import ru.furpuro.known_legends.blocks.ModBlocks
import ru.furpuro.known_legends.data.ModAttachments

object Functions {
    fun spawnGlitchAir(level: ServerLevel, player: ServerPlayer) {
        val pos = player.blockPosition()
        val attempts = 75
        var foundPos: BlockPos? = null
        for (i in 1..attempts) {
            val candidate = pos.offset(
                level.random.nextInt(-100, 101).coerceIn(level.worldBorder.minX.toInt(), level.worldBorder.maxX.toInt()),
                level.random.nextInt(-50, 51).coerceIn(level.minY, level.maxY),
                level.random.nextInt(-100, 101).coerceIn(level.worldBorder.minZ.toInt(), level.worldBorder.maxZ.toInt())
            )

            val targetState = level.getBlockState(candidate)

            val blockId = BuiltInRegistries.BLOCK.getKey(targetState.block).toString()

            if (!targetState.isAir && hasAirNeighbor(level, candidate) && !blockId.contains("hermetic")) {
                foundPos = candidate
                break
            }
        }
        if (foundPos != null) {
            level.setBlock(foundPos, ModBlocks.GLITCH_AIR.get().defaultBlockState(), 2)
            level.players().forEach { p ->
                p.sendSystemMessage(Component.translatable("message.known_legends.broke"))
            }
        } else {
            level.players().forEach { p ->
                p.sendSystemMessage(Component.translatable("message.known_legends.knocking"))
            }
        }
    }
    private fun getRandomNeighbor(pos: BlockPos): BlockPos {
        val directions = listOf(
            BlockPos(1, 0, 0),
            BlockPos(-1, 0, 0),
            BlockPos(0, 1, 0),
            BlockPos(0, -1, 0),
            BlockPos(0, 0, 1),
            BlockPos(0, 0, -1)
        )

        return pos.offset(directions.random())
    }
    fun spreadGlitch(pos: BlockPos,level: ServerLevel,random: RandomSource) {
        val targetPos = getRandomNeighbor(pos)

        val targetState = level.getBlockState(targetPos)

        val blockId = BuiltInRegistries.BLOCK.getKey(targetState.block).toString()

        val data = level.getData(ModAttachments.POINTS_DATA)
        val phase = getPhase(data.points)

        if ( ( (blockId.contains("glitch") && blockId.contains("decor")) || !blockId.contains("glitch") ) && !blockId.contains("hermetic")) {

            if (phase >= 1) {
                if (80 >= random.nextIntBetweenInclusive(1, 100)) {
                    if (targetState.isSolidRender && !targetState.isAir && hasAirNeighbor(level, pos)) {
                        if (targetState.`is`(BlockTags.DIRT)) {
                            level.setBlock(targetPos, ModBlocks.GLITCH_DIRT.get().defaultBlockState(), 2)
                        } else if (targetState.`is`(BlockTags.BASE_STONE_OVERWORLD)) {
                            level.setBlock(targetPos, ModBlocks.GLITCH_STONE.get().defaultBlockState(), 2)
                        } else if (targetState.`is`(BlockTags.LOGS)) {
                            level.setBlock(targetPos, ModBlocks.GLITCH_LOG.get().defaultBlockState().setValue(BlockStateProperties.AXIS,targetState.getValue(BlockStateProperties.AXIS)), 2)
                        } else {
                            level.setBlock(targetPos, ModBlocks.GLITCH_BLOCK.get().defaultBlockState(), 2)
                        }
                    } else if (targetState.`is`(BlockTags.LEAVES)) {
                        level.setBlock(targetPos, ModBlocks.GLITCH_LEAVES.get().defaultBlockState(), 2)
                    }
                } else if (!targetState.`is`(Blocks.FIRE)) {
                    level.destroyBlock(targetPos,false)
                    level.setBlock(targetPos, ModBlocks.GLITCH_AIR.get().defaultBlockState(), 2)
                }
            }
            if (phase >= 0) {
                if (targetState.isAir || blockId.contains("door")) {
                    level.setBlock(targetPos, ModBlocks.GLITCH_AIR.get().defaultBlockState(), 2)
                }
            }

        } else if (blockId.contains("hermetic") && blockId.contains("glass")) {
            if (1 >= random.nextInt(1,400)) { // 0.25%
                level.destroyBlock(targetPos,false)
                level.setBlock(targetPos, ModBlocks.GLITCH_AIR.get().defaultBlockState(), 2)
            }
        }

    }
    fun spreadFixGas(pos: BlockPos,level: ServerLevel,random: RandomSource) {
        val targetPos = getRandomNeighbor(pos)

        val targetState = level.getBlockState(targetPos)

        val blockId = BuiltInRegistries.BLOCK.getKey(targetState.block).toString()

        if (blockId.contains("glitch") && !blockId.contains("decor")) {
            level.setBlock(targetPos, Blocks.AIR.defaultBlockState(), 2)
            level.setBlock(pos,Blocks.AIR.defaultBlockState(),2)
        }
        if (targetState.isAir) {
            if (95 >= random.nextIntBetweenInclusive(1,100)) {
                level.setBlock(targetPos, ModBlocks.FIX_GAS.get().defaultBlockState(), 2)
                if (99 >= random.nextIntBetweenInclusive(1,100)) {
                    level.setBlock(pos,Blocks.AIR.defaultBlockState(),2)
                }
            } else {
                level.setBlock(pos,Blocks.AIR.defaultBlockState(),2)
            }
        }
    }
    private fun hasAirNeighbor(level: Level, pos: BlockPos): Boolean {
        val directions = listOf(
            BlockPos(1, 0, 0),
            BlockPos(-1, 0, 0),
            BlockPos(0, 1, 0),
            BlockPos(0, -1, 0),
            BlockPos(0, 0, 1),
            BlockPos(0, 0, -1)
        )

        return directions.any { offset ->
            level.getBlockState(pos.offset(offset)).isAir
        }
    }
    fun getPhase(points : Int): Int {
        return if (points < 800) {
            0
        } else if (points < 75000) {
            1
        } else if (points < 450000) {
            2
        } else {
            3
        }
    }
    fun glitchRemoveFunction(level: Level, pos: BlockPos) {
        if (!level.isClientSide) {
            val data = level.getData(ModAttachments.POINTS_DATA)
            data.points -= 2

            level.setBlock(pos, ModBlocks.GLITCH_AIR.get().defaultBlockState(), 2)
        }
    }
}