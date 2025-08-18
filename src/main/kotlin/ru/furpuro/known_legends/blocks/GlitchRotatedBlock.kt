package ru.furpuro.known_legends.blocks

import com.mojang.serialization.MapCodec
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.EnumProperty

open class GlitchRotatedBlock(props: Properties) : GlitchBlock(props.randomTicks()) {
    companion object {
        val CODEC: MapCodec<RotatedPillarBlock> = simpleCodec(::RotatedPillarBlock)
        val AXIS: EnumProperty<Direction.Axis> = BlockStateProperties.AXIS
    }

    override fun codec(): MapCodec<out RotatedPillarBlock> {
        return CODEC
    }

    init {
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y))
    }

    override fun rotate(state: BlockState, rot: Rotation): BlockState {
        return rotatePillar(state, rot)
    }

    private fun rotatePillar(state: BlockState, rotation: Rotation): BlockState {
        return when (rotation) {
            Rotation.COUNTERCLOCKWISE_90, Rotation.CLOCKWISE_90 -> {
                when (state.getValue(AXIS)) {
                    Direction.Axis.X -> state.setValue(AXIS, Direction.Axis.Z)
                    Direction.Axis.Z -> state.setValue(AXIS, Direction.Axis.X)
                    else -> state
                }
            }
            else -> state
        }
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(AXIS)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        return this.defaultBlockState().setValue(AXIS, context.clickedFace.axis)
    }
}