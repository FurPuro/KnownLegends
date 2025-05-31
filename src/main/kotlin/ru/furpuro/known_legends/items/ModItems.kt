package ru.furpuro.known_legends.items

import net.minecraft.world.item.Item
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.equipment.ArmorType
import net.neoforged.neoforge.registries.DeferredRegister
import ru.furpuro.known_legends.Known_legends
import ru.furpuro.known_legends.blocks.ModBlocks
import ru.furpuro.known_legends.custom.ModMaterials

object ModItems {
    val REGISTRY = DeferredRegister.createItems(Known_legends.ID)

    val GLITCH_BLOCK_DECOR = REGISTRY.registerSimpleBlockItem(
        "glitch_block_decor",
        ModBlocks.GLITCH_BLOCK_DECOR,
        Item.Properties()
    )

    val GLITCH_BLOCK = REGISTRY.registerSimpleBlockItem(
        "glitch_block",
        ModBlocks.GLITCH_BLOCK,
        Item.Properties()
    )

    val GLITCH_LEAVES = REGISTRY.registerSimpleBlockItem(
        "glitch_leaves",
        ModBlocks.GLITCH_LEAVES,
        Item.Properties()
    )

    val FIX_GAS_SPRAYER = REGISTRY.registerSimpleBlockItem(
        "fix_gas_sprayer",
        ModBlocks.FIX_GAS_SPRAYER,
        Item.Properties()
    )

    val GAS_MASK = REGISTRY.registerItem("gas_mask") { properties ->
        Item(
            properties
                .humanoidArmor(ModMaterials.PROTECTIVE_SUIT,ArmorType.HELMET)
                .stacksTo(1)
        )
    }

    val PROTECTIVE_BOOTS = REGISTRY.registerItem("protective_boots") { properties ->
        Item(
            properties
                .humanoidArmor(ModMaterials.PROTECTIVE_SUIT,ArmorType.BOOTS)
                .stacksTo(1)
        )
    }

    val FIX_POWDER = REGISTRY.registerItem("fix_powder") { properties ->
        Item(
            properties
                .fireResistant()
                .stacksTo(48)
                .rarity(Rarity.UNCOMMON)
        )
    }
}