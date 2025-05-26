package ru.furpuro.known_legends.items

import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredRegister
import ru.furpuro.known_legends.Known_legends
import ru.furpuro.known_legends.block.ModBlocks

object ModItems {
    val REGISTRY = DeferredRegister.createItems(Known_legends.ID)

    val ERROR_BLOCK_DECOR = REGISTRY.registerSimpleBlockItem(
        "error_block_decor",
        ModBlocks.ERROR_BLOCK_DECOR,
        Item.Properties()
            .fireResistant()
    )

    val ERROR_BLOCK = REGISTRY.registerSimpleBlockItem(
        "error_block",
        ModBlocks.ERROR_BLOCK,
        Item.Properties()
            .fireResistant()
    )
}