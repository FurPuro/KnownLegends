package ru.furpuro.known_legends.blocks

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.redstone.Orientation
import net.minecraft.world.ticks.TickPriority

class FixGasSprayer(props: Properties) : Block(props) {
    companion object {
        val POWERED: BooleanProperty = BlockStateProperties.POWERED
    }

    init {
        registerDefaultState(this.stateDefinition.any().setValue(POWERED, false))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(POWERED)
    }

    override fun neighborChanged(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        block: Block,
        orientation: Orientation?,
        isMoving: Boolean
    ) {
        if (!level.isClientSide) {
            val powered = level.hasNeighborSignal(pos)
            if (powered && !state.getValue(POWERED)) {
                level.setBlock(pos, state.setValue(POWERED, true), 2)
            } else if (!powered && state.getValue(POWERED)) {
                level.setBlock(pos, state.setValue(POWERED, false), 2)
            }
        }
        super.neighborChanged(state, level, pos, block, orientation, isMoving)
    }

    private fun spreadAir(level: Level, pos: BlockPos) {
        val airBlock = ModBlocks.FIX_GAS.get()
        for (dx in -1..1) for (dy in -1..1) for (dz in -1..1) {
            val target = pos.offset(dx, dy, dz)
            if (level.getBlockState(target).isAir) {
                level.setBlock(target, airBlock.defaultBlockState(), 3)
            }
        }
    }

    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, movedByPiston: Boolean) {
        if (!level.isClientSide()) {
            level.scheduleTick(pos, this, 30, TickPriority.NORMAL)
        }
        super.onPlace(state, level, pos, oldState, movedByPiston)
    }

    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (!level.isClientSide && state.getValue(POWERED)) {
            spreadAir(level, pos)
            level.scheduleTick(pos, this, 30, TickPriority.NORMAL)
        }
        super.tick(state, level, pos, random)
    }
}