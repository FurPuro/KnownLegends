package ru.furpuro.known_legends.block

import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.registries.DeferredRegister
import ru.furpuro.known_legends.Known_legends

object ModBlocks {
    val REGISTRY = DeferredRegister.createBlocks(Known_legends.ID)

    val ERROR_BLOCK_DECOR = REGISTRY.registerBlock("error_block_decor") { properties ->
        Block(properties
            .strength(1.2f)
            .requiresCorrectToolForDrops()
        )
    }

    val ERROR_BLOCK = REGISTRY.registerBlock("error_block") { properties ->
        ErrorBlock(properties
            .strength(1.6f,1000f)
            .requiresCorrectToolForDrops()
            .randomTicks()
        )
    }
}