package ru.furpuro.known_legends.block

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

class ErrorBlock(props: Properties) : Block(props.randomTicks()) {
    public override fun randomTick(state: BlockState, world: ServerLevel, pos: BlockPos, random: RandomSource) {
        val targetPos = pos.offset(
            random.nextIntBetweenInclusive(-1,1),
            random.nextIntBetweenInclusive(-1,1),
            random.nextIntBetweenInclusive(-1,1)
        )

        val targetState = world.getBlockState(targetPos)

        if (targetState.isSolidRender && !targetState.isAir) {
            world.setBlock(targetPos, this.defaultBlockState(), 2)
        }
    }
}