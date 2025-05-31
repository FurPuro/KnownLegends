package ru.furpuro.known_legends.blocks

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import net.minecraft.world.ticks.TickPriority
import ru.furpuro.known_legends.custom.Functions.spreadFixGas

class FixGas(props: Properties) : Block(props.air().noOcclusion().noLootTable().noCollission().replaceable()) {
    override fun skipRendering(state: BlockState, adjacentState: BlockState, side: Direction): Boolean {
        return adjacentState.`is`(this)
    }

    override fun getLightBlock(state: BlockState): Int = 0

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return Shapes.empty()
    }

    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, movedByPiston: Boolean) {
        if (!level.isClientSide()) {
            level.scheduleTick(pos, this, 2, TickPriority.NORMAL)
        }
        super.onPlace(state, level, pos, oldState, movedByPiston)
    }

    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (!level.isClientSide()) {
            spreadFixGas(pos,level,random)
            level.scheduleTick(pos, this, 2, TickPriority.NORMAL)
        }
        super.tick(state, level, pos, random)
    }
}