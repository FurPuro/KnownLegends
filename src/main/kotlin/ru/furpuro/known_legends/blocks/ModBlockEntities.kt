package ru.furpuro.known_legends.blocks

import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.neoforge.registries.DeferredRegister
import net.minecraft.core.registries.BuiltInRegistries
import net.neoforged.neoforge.registries.DeferredHolder

object ModBlockEntities {
    val BLOCK_ENTITY_TYPES: DeferredRegister<BlockEntityType<*>> =
        DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, "known_legends")

    // Регистрация BlockEntity для FixGasSprayer
    val FIX_GAS_SPRAYER: DeferredHolder<BlockEntityType<*>, BlockEntityType<FixGasSprayerEntity>> =
        BLOCK_ENTITY_TYPES.register("fix_gas_sprayer") { ->
            BlockEntityType(::FixGasSprayerEntity, setOf(ModBlocks.FIX_GAS_SPRAYER.get()), false)
        }
}