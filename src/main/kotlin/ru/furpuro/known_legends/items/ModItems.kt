package ru.furpuro.known_legends.items

import net.minecraft.world.item.*
import net.minecraft.world.item.equipment.ArmorType
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import ru.furpuro.known_legends.Known_legends
import ru.furpuro.known_legends.blocks.ModBlocks
import ru.furpuro.known_legends.custom.ModMaterials
import ru.furpuro.known_legends.entities.ModEntityTypes
import ru.furpuro.known_legends.fuids.ModFluids
import ru.furpuro.known_legends.items.custom.GlitchSword

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

    val GLITCH_ORE: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "glitch_ore",
        ModBlocks.GLITCH_ORE,
        Item.Properties()
    )

    val GLITCH_LEAVES: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "glitch_leaves",
        ModBlocks.GLITCH_LEAVES,
        Item.Properties()
    )

    val GLITCH_DIRT: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "glitch_dirt",
        ModBlocks.GLITCH_DIRT,
        Item.Properties()
    )

    val GLITCH_STONE: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "glitch_stone",
        ModBlocks.GLITCH_STONE,
        Item.Properties()
    )

    val GLITCH_SAND: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "glitch_sand",
        ModBlocks.GLITCH_SAND,
        Item.Properties()
    )

    val GLITCH_GRAVEL: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "glitch_gravel",
        ModBlocks.GLITCH_GRAVEL,
        Item.Properties()
    )

    val GLITCH_LOG: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "glitch_log",
        ModBlocks.GLITCH_LOG,
        Item.Properties()
    )

    val GLITCH_STAIRS: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "glitch_stairs",
        ModBlocks.GLITCH_STAIRS,
        Item.Properties()
    )

    val GLITCH_SLAB: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "glitch_slab",
        ModBlocks.GLITCH_SLAB,
        Item.Properties()
    )

    val GLITCH_LASER: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "glitch_laser",
        ModBlocks.GLITCH_LASER,
        Item.Properties()
    )

    val GLITCH_FENCE: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "glitch_fence",
        ModBlocks.GLITCH_FENCE,
        Item.Properties()
    )

    val GLITCH_GRASS: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "glitch_grass",
        ModBlocks.GLITCH_GRASS,
        Item.Properties()
    )

    val GLITCH_GLASS: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "glitch_glass",
        ModBlocks.GLITCH_GLASS,
        Item.Properties()
    )

    val GLITCH_GLASS_PANE: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "glitch_glass_pane",
        ModBlocks.GLITCH_GLASS_PANE,
        Item.Properties()
    )

    val GLITCH_PLANKS: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "glitch_planks",
        ModBlocks.GLITCH_PLANKS,
        Item.Properties()
    )

    val GLITCH_COBBLESTONE: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "glitch_cobblestone",
        ModBlocks.GLITCH_COBBLESTONE,
        Item.Properties()
    )

    val GLITCH_SHARD: DeferredItem<Item> = REGISTRY.registerItem("glitch_shard") { properties ->
        Item(
            properties
                .rarity(Rarity.UNCOMMON)
                .fireResistant()
        )
    }

    val GLITCH_SWORD: DeferredItem<Item> = REGISTRY.registerItem("glitch_sword") { properties ->
        GlitchSword(
            ModToolMaterials.GLITCH,4.2f,-2.2f,
            properties
                .rarity(Rarity.UNCOMMON)
                .fireResistant()
        )
    }

    val GLITCH_PARASITE_SPAWN_EGG: DeferredItem<Item> = REGISTRY.registerItem("glitch_parasite_spawn_egg") { properties ->
        SpawnEggItem(
            ModEntityTypes.GLITCH_PARASITE.get(),
            properties
        )
    }

    val GLITCH_ENTITY_SPAWN_EGG: DeferredItem<Item> = REGISTRY.registerItem("glitch_entity_spawn_egg") { properties ->
        SpawnEggItem(
            ModEntityTypes.GLITCH_ENTITY.get(),
            properties
        )
    }

    val GLITCH_CRAWLER_SPAWN_EGG: DeferredItem<Item> = REGISTRY.registerItem("glitch_crawler_spawn_egg") { properties ->
        SpawnEggItem(
            ModEntityTypes.GLITCH_CRAWLER.get(),
            properties
        )
    }

    val FIX_GAS_SPRAYER: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "fix_gas_sprayer",
        ModBlocks.FIX_GAS_SPRAYER,
        Item.Properties()
    )

    val HERMETIC_WALL: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "hermetic_wall",
        ModBlocks.HERMETIC_WALL,
        Item.Properties()
    )

    val CRACKED_HERMETIC_WALL: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "cracked_hermetic_wall",
        ModBlocks.CRACKED_HERMETIC_WALL,
        Item.Properties()
    )

    val HERMETIC_DOOR: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "hermetic_door",
        ModBlocks.HERMETIC_DOOR,
        Item.Properties()
    )

    val HERMETIC_GLASS: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "hermetic_glass",
        ModBlocks.HERMETIC_GLASS,
        Item.Properties()
    )

    val CRACKED_HERMETIC_GLASS: DeferredItem<BlockItem> = REGISTRY.registerSimpleBlockItem(
        "cracked_hermetic_glass",
        ModBlocks.CRACKED_HERMETIC_GLASS,
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