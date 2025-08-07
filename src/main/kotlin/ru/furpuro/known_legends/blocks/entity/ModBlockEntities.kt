package ru.furpuro.known_legends.blocks.entity

import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import ru.furpuro.known_legends.Known_legends
import ru.furpuro.known_legends.blocks.ModBlocks

object ModBlockEntities {
    val BLOCK_ENTITIES: DeferredRegister<BlockEntityType<*>> = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE,Known_legends.ID)

    val FIX_GAS_SPRAYER_BE: DeferredHolder<BlockEntityType<*>, BlockEntityType<FixGasSprayerEntity>> = BLOCK_ENTITIES.register("fix_gas_sprayer_be") {->
        BlockEntityType(
            ::FixGasSprayerEntity,
            ModBlocks.FIX_GAS_SPRAYER.get()
        )
    }
}