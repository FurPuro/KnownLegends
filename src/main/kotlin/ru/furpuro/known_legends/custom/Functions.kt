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
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.FenceBlock
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.StairBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import ru.furpuro.known_legends.blocks.ModBlocks
import ru.furpuro.known_legends.data.ModAttachments
import ru.furpuro.known_legends.items.ModItems

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

        placeGlitch(blockId,phase, random, targetState, level, pos, targetPos)
    }
    fun placeGlitch(blockId: String,phase: Int,random: RandomSource,targetState: BlockState,level: ServerLevel,pos: BlockPos,targetPos: BlockPos) {
        if ( ( (blockId.contains("glitch") && blockId.contains("decor")) || !blockId.contains("glitch") ) && !blockId.contains("hermetic")) {
            if (phase >= 2) {
                if (10 >= random.nextIntBetweenInclusive(1,100)) {
                    if (level.getBlockState(pos.above()).isAir && level.getBlockState(pos).isSolidRender) {
                        level.setBlock(pos.above(),ModBlocks.GLITCH_GRASS.get().defaultBlockState(),2)
                    }
                }
            }
            if (phase >= 1) {
                if (80 >= random.nextIntBetweenInclusive(1, 100)) {
                    if (targetState.isSolidRender && !targetState.isAir && hasAirNeighbor(level, pos)) {
                        if (targetState.`is`(BlockTags.DIRT)) {
                            level.setBlock(targetPos, ModBlocks.GLITCH_DIRT.get().defaultBlockState(), 2)
                        } else if (targetState.`is`(BlockTags.BASE_STONE_OVERWORLD)) {
                            level.setBlock(targetPos, ModBlocks.GLITCH_STONE.get().defaultBlockState(), 2)
                        } else if (blockId.contains("sand")) {
                            level.setBlock(targetPos, ModBlocks.GLITCH_SAND.get().defaultBlockState(), 2)
                        } else if (blockId.contains("gravel")) {
                            level.setBlock(targetPos, ModBlocks.GLITCH_GRAVEL.get().defaultBlockState(), 2)
                        } else if (targetState.`is`(BlockTags.LOGS)) {
                            level.setBlock(targetPos, ModBlocks.GLITCH_LOG.get().defaultBlockState().setValue(BlockStateProperties.AXIS,targetState.getValue(BlockStateProperties.AXIS)), 2)
                        } else {
                            level.setBlock(targetPos, ModBlocks.GLITCH_BLOCK.get().defaultBlockState(), 2)
                        }
                    } else if (targetState.`is`(BlockTags.LEAVES)) {
                        level.setBlock(targetPos, ModBlocks.GLITCH_LEAVES.get().defaultBlockState(), 2)
                    } else if (targetState.`is`(BlockTags.FENCES)) {
                        val waterlogged = targetState.getValue(FenceBlock.WATERLOGGED)
                        val east = targetState.getValue(FenceBlock.EAST)
                        val west = targetState.getValue(FenceBlock.WEST)
                        val north = targetState.getValue(FenceBlock.NORTH)
                        val south = targetState.getValue(FenceBlock.SOUTH)

                        level.setBlock(targetPos, ModBlocks.GLITCH_FENCE.get().defaultBlockState().setValue(FenceBlock.WATERLOGGED,waterlogged).setValue(FenceBlock.EAST,east).setValue(FenceBlock.WEST,west).setValue(FenceBlock.NORTH,north).setValue(FenceBlock.SOUTH,south), 2)
                    } else if (targetState.`is`(BlockTags.STAIRS)) {
                        val facing = targetState.getValue(StairBlock.FACING)
                        val half = targetState.getValue(StairBlock.HALF)
                        val shape = targetState.getValue(StairBlock.SHAPE)
                        val waterlogged = targetState.getValue(StairBlock.WATERLOGGED)

                        level.setBlock(targetPos, ModBlocks.GLITCH_STAIRS.get().defaultBlockState().setValue(StairBlock.FACING,facing).setValue(StairBlock.HALF,half).setValue(StairBlock.SHAPE,shape).setValue(StairBlock.WATERLOGGED,waterlogged), 2)
                    } else if (targetState.`is`(BlockTags.SLABS)) {
                        val slabType = targetState.getValue(SlabBlock.TYPE)
                        val waterlogged = targetState.getValue(SlabBlock.WATERLOGGED)

                        level.setBlock(targetPos, ModBlocks.GLITCH_SLAB.get().defaultBlockState().setValue(SlabBlock.TYPE,slabType).setValue(SlabBlock.WATERLOGGED,waterlogged), 2)
                    }
                } else if (!targetState.`is`(Blocks.FIRE)) {
//                    if (90 >= random.nextIntBetweenInclusive(1,100)) {
//                        if (hasAirNeighbor(level, pos) && !targetState.isAir) {
//                            val targetPos2 = getRandomNeighbor(pos)
//                            val targetState2 = level.getBlockState(targetPos2)
//
//                            if (targetState2.isAir) {
//                                level.setBlock(targetPos2,targetState,2)
//                                level.setBlock(targetPos,Blocks.AIR.defaultBlockState(),2)
//                            }
//                        }
//                    } else {
                        level.destroyBlock(targetPos,false)
                        level.setBlock(targetPos, ModBlocks.GLITCH_AIR.get().defaultBlockState(), 2)
//                    }
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
    fun glitchBlockStep(entity:Entity,level:Level) {
        if (entity is LivingEntity) {
            if (entity.getItemBySlot(EquipmentSlot.FEET).item != ModItems.PROTECTIVE_BOOTS.get()) {
                val effect = MobEffectInstance(MobEffects.SLOWNESS, 10, 2, false, false, true)
                entity.addEffect(effect)
                val effect2 = MobEffectInstance(MobEffects.MINING_FATIGUE, 10, 2, false, false, true)
                entity.addEffect(effect2)
            } else if (10 >= level.random.nextIntBetweenInclusive(1,100)) {
                entity.getItemBySlot(EquipmentSlot.FEET).hurtAndBreak(
                    1,
                    entity,
                    EquipmentSlot.FEET
                )
            }
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