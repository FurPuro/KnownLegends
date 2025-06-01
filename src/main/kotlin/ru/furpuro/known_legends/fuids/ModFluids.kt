package ru.furpuro.known_legends.fuids

import net.minecraft.core.registries.Registries
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.material.Fluid
import net.neoforged.neoforge.common.SoundActions
import net.neoforged.neoforge.fluids.FluidType
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries
import ru.furpuro.known_legends.Known_legends.ID
import ru.furpuro.known_legends.blocks.ModBlocks
import ru.furpuro.known_legends.items.ModItems

object ModFluids {
    val FLUIDS: DeferredRegister<Fluid> = DeferredRegister.create(Registries.FLUID, ID)
    val FLUID_TYPES: DeferredRegister<FluidType> = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, ID)

//    val GLITCH_FLUID_TYPE: DeferredHolder<FluidType, FluidType> = FLUID_TYPES.register("glitch_fluid_type") { ->
//        FluidType(
//            FluidType.Properties.create()
//                .density(1500)
//                .viscosity(2000)
//                .temperature(500)
//                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
//                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
//                .canSwim(false)
//                .canDrown(true)
//                .canHydrate(false)
//                .lightLevel(0)
//                .rarity(Rarity.EPIC)
//        )
//    }
//
//    val GLITCH_FLUID: DeferredHolder<Fluid, SourceGlitchFluid> = FLUIDS.register("glitch_fluid") { -> SourceGlitchFluid() }
//
//    val GLITCH_FLUID_FLOW: DeferredHolder<Fluid, FlowingGlitchFluid> = FLUIDS.register("glitch_fluid_flow") { -> FlowingGlitchFluid() }
}