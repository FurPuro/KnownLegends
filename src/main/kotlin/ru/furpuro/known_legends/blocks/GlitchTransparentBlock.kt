package ru.furpuro.known_legends.blocks

import net.minecraft.core.Direction
import net.minecraft.world.level.block.state.BlockState

class GlitchTransparentBlock(props: Properties) : GlitchBlock(props.randomTicks()) {
    override fun propagatesSkylightDown(state: BlockState): Boolean = true
    override fun getLightBlock(state: BlockState): Int = 1
    override fun skipRendering(state: BlockState, adjacentState: BlockState, side: Direction): Boolean {
        return adjacentState.`is`(this)
    }
}