package ru.furpuro.known_legends.items

import net.minecraft.world.item.*
import net.minecraft.world.item.equipment.ArmorType
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import ru.furpuro.known_legends.Known_legends
import ru.furpuro.known_legends.blocks.ModBlocks
import ru.furpuro.known_legends.custom.ModMaterials
import ru.furpuro.known_legends.fuids.ModFluids

object ModItems {
    val REGISTRY: DeferredRegister.Items = DeferredRegister.createItems(Known_legends.ID)

    val GLITCH_BLOCK_DECOR: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "glitch_block_decor",
        ModBlocks.GLITCH_BLOCK_DECOR,
        Item.Properties()
    )

    val GLITCH_BLOCK: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "glitch_block",
        ModBlocks.GLITCH_BLOCK,
        Item.Properties()
    )

    val GLITCH_LEAVES: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "glitch_leaves",
        ModBlocks.GLITCH_LEAVES,
        Item.Properties()
    )

    val FIX_GAS_SPRAYER: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "fix_gas_sprayer",
        ModBlocks.FIX_GAS_SPRAYER,
        Item.Properties()
    )

    val GAS_MASK: DeferredItem<Item> = REGISTRY.registerItem("gas_mask") { properties ->
        Item(
            properties
                .humanoidArmor(ModMaterials.PROTECTIVE_SUIT,ArmorType.HELMET)
                .stacksTo(1)
        )
    }

    val PROTECTIVE_BOOTS: DeferredItem<Item> = REGISTRY.registerItem("protective_boots") { properties ->
        Item(
            properties
                .humanoidArmor(ModMaterials.PROTECTIVE_SUIT,ArmorType.BOOTS)
                .stacksTo(1)
        )
    }

    val FIX_POWDER: DeferredItem<Item> = REGISTRY.registerItem("fix_powder") { properties ->
        Item(
            properties
                .fireResistant()
                .stacksTo(48)
                .rarity(Rarity.UNCOMMON)
        )
    }

//    val GLITCH_FLUID_BUCKET: DeferredItem<BucketItem> = REGISTRY.registerItem("glitch_fluid_bucket") { properties ->
//        BucketItem(
//            ModFluids.GLITCH_FLUID.get(),
//            properties
//                .stacksTo(1)
//                .craftRemainder(Items.BUCKET)
//                .rarity(Rarity.EPIC)
//                .fireResistant()
//        )
//    }
}