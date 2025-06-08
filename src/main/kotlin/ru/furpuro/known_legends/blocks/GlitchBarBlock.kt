package ru.furpuro.known_legends.blocks

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.IronBarsBlock
import net.minecraft.world.level.block.state.BlockState
import ru.furpuro.known_legends.custom.Functions.glitchBlockStep
import ru.furpuro.known_legends.custom.Functions.spreadGlitch
import ru.furpuro.known_legends.data.ModAttachments

class GlitchBarBlock(props: Properties) : IronBarsBlock(props.randomTicks()) {
    override fun propagatesSkylightDown(state: BlockState): Boolean = true
    override fun getLightBlock(state: BlockState): Int = 1
    override fun skipRendering(state: BlockState, adjacentState: BlockState, side: Direction): Boolean {
        return adjacentState.`is`(this)
    }

    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, movedByPiston: Boolean) {
        if (!level.isClientSide()) {
            val data = level.getData(ModAttachments.POINTS_DATA)
            data.points++
        }

        super.onPlace(state, level, pos, oldState, movedByPiston)
    }

    override fun stepOn(level: Level, pos: BlockPos, state: BlockState, entity: Entity) {
        if (!level.isClientSide()) {
            glitchBlockStep(entity,level)
        }

        super.stepOn(level, pos, state, entity)
    }

    public override fun randomTick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (!level.isClientSide()) {
            spreadGlitch(pos,level,random)
        }

        super.randomTick(state, level, pos, random)
    }
}