//package ru.furpuro.known_legends.fuids
//
//import net.minecraft.core.BlockPos
//import net.minecraft.core.Direction
//import net.minecraft.server.level.ServerLevel
//import net.minecraft.world.item.Item
//import net.minecraft.world.level.BlockGetter
//import net.minecraft.world.level.LevelAccessor
//import net.minecraft.world.level.LevelReader
//import net.minecraft.world.level.block.LiquidBlock
//import net.minecraft.world.level.block.state.BlockState
//import net.minecraft.world.level.material.FlowingFluid
//import net.minecraft.world.level.material.Fluid
//import net.minecraft.world.level.material.FluidState
//import net.neoforged.neoforge.fluids.FluidType
//import ru.furpuro.known_legends.blocks.ModBlocks
//import ru.furpuro.known_legends.items.ModItems
//
//class SourceGlitchFluid : FlowingFluid() {
//
//    override fun getBucket(): Item = ModItems.GLITCH_FLUID_BUCKET.get()
//
//    override fun canBeReplacedWith(state: FluidState, level: BlockGetter, pos: BlockPos,
//                                   fluid: Fluid, direction: Direction): Boolean {
//        return direction == Direction.DOWN && !fluid.isSame(this)
//    }
//
//    override fun isSource(state: FluidState): Boolean = true
//    override fun getAmount(state: FluidState): Int = 8
//    override fun getTickDelay(level: LevelReader): Int = 10
//
//    override fun createLegacyBlock(state: FluidState): BlockState {
//        return ModBlocks.GLITCH_FLUID_BLOCK.get().defaultBlockState()
//            .setValue(LiquidBlock.LEVEL, getLegacyLevel(state))
//    }
//
//    override fun getDropOff(level: LevelReader): Int = 1
//    override fun getFluidType(): FluidType = ModFluids.GLITCH_FLUID_TYPE.get()
//
//    @Deprecated("Deprecated in Java", ReplaceWith("false"))
//    override fun canConvertToSource(level: ServerLevel): Boolean = false
//
//    override fun getFlowing(): FlowingFluid = ModFluids.GLITCH_FLUID_FLOW.get()
//    override fun getSource(): FlowingFluid = this
//
//    override fun beforeDestroyingBlock(level: LevelAccessor, pos: BlockPos, state: BlockState) {
//        level.destroyBlock(pos, true)
//    }
//
//    override fun getSlopeFindDistance(level: LevelReader): Int = 4
//    override fun getExplosionResistance(): Float = 1000f
//}