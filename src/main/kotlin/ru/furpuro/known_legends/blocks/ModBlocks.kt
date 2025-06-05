package ru.furpuro.known_legends.blocks

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DoorBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.StairBlock
import net.minecraft.world.level.block.TransparentBlock
import net.minecraft.world.level.block.state.properties.BlockSetType
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredRegister
import ru.furpuro.known_legends.Known_legends

object ModBlocks {
    val REGISTRY: DeferredRegister.Blocks = DeferredRegister.createBlocks(Known_legends.ID)

    val GLITCH_BLOCK_DECOR: DeferredBlock<Block> = REGISTRY.registerBlock("glitch_block_decor") { properties ->
        Block(properties
            .strength(1.5f,2f)
            .requiresCorrectToolForDrops()
        )
    }

    val GLITCH_BLOCK: DeferredBlock<GlitchBlock> = REGISTRY.registerBlock("glitch_block") { properties ->
        GlitchBlock(properties
            .strength(1.6f,1000f)
            .requiresCorrectToolForDrops()
            .randomTicks()
            .sound(SoundType.MUD_BRICKS)
        )
    }

    val GLITCH_DIRT: DeferredBlock<GlitchBlock> = REGISTRY.registerBlock("glitch_dirt") { properties ->
        GlitchBlock(properties
            .strength(1.2f,1000f)
            .randomTicks()
            .sound(SoundType.MUD)
            .requiresCorrectToolForDrops()
        )
    }

    val GLITCH_STONE: DeferredBlock<GlitchBlock> = REGISTRY.registerBlock("glitch_stone") { properties ->
        GlitchBlock(properties
            .strength(1.8f,1000f)
            .randomTicks()
            .sound(SoundType.PACKED_MUD)
            .requiresCorrectToolForDrops()
        )
    }

    val GLITCH_SAND: DeferredBlock<GlitchBlock> = REGISTRY.registerBlock("glitch_sand") { properties ->
        GlitchBlock(properties
            .strength(1f,1000f)
            .randomTicks()
            .sound(SoundType.SOUL_SAND)
            .requiresCorrectToolForDrops()
        )
    }

    val GLITCH_GRAVEL: DeferredBlock<GlitchBlock> = REGISTRY.registerBlock("glitch_gravel") { properties ->
        GlitchBlock(properties
            .strength(1.1f,1000f)
            .randomTicks()
            .sound(SoundType.SUSPICIOUS_GRAVEL)
            .requiresCorrectToolForDrops()
        )
    }

    val GLITCH_GRASS: DeferredBlock<GlitchBlock> = REGISTRY.registerBlock("glitch_grass") { properties ->
        GlitchGrassBlock(properties
            .randomTicks()
            .sound(SoundType.GRASS)
            .instabreak()
            .noCollission()
            .noLootTable()
            .replaceable()
        )
    }

    val GLITCH_STAIRS: DeferredBlock<GlitchStairsBlock> = REGISTRY.registerBlock("glitch_stairs") { properties ->
        GlitchStairsBlock(
            GLITCH_BLOCK.get().defaultBlockState(),
            properties
            .strength(1.4f,1000f)
            .randomTicks()
            .sound(SoundType.MUD_BRICKS)
            .requiresCorrectToolForDrops()
        )
    }

    val GLITCH_SLAB: DeferredBlock<GlitchSlabBlock> = REGISTRY.registerBlock("glitch_slab") { properties ->
        GlitchSlabBlock(
            properties
                .strength(1.3f,1000f)
                .randomTicks()
                .sound(SoundType.MUD_BRICKS)
                .requiresCorrectToolForDrops()
        )
    }

    val GLITCH_FENCE: DeferredBlock<GlitchFenceBlock> = REGISTRY.registerBlock("glitch_fence") { properties ->
        GlitchFenceBlock(
            properties
                .strength(1.3f,1000f)
                .randomTicks()
                .sound(SoundType.MUD_BRICKS)
                .requiresCorrectToolForDrops()
        )
    }

    val GLITCH_LOG: DeferredBlock<GlitchBlock> = REGISTRY.registerBlock("glitch_log") { properties ->
        GlitchRotatedBlock(properties
            .strength(1.5f,1000f)
            .randomTicks()
            .sound(SoundType.PACKED_MUD)
            .requiresCorrectToolForDrops()
        )
    }

    val GLITCH_LEAVES: DeferredBlock<GlitchTransparentBlock> = REGISTRY.registerBlock("glitch_leaves") { properties ->
        GlitchTransparentBlock(properties
            .strength(0.3f,1000f)
            .randomTicks()
            .sound(SoundType.GRASS)
            .noOcclusion()
        )
    }

    val GLITCH_AIR: DeferredBlock<GlitchAir> = REGISTRY.registerBlock("glitch_air") { properties ->
        GlitchAir(properties
            .replaceable()
            .noCollission()
            .noLootTable()
            .air()
            .noOcclusion()
        )
    }

    val FIX_GAS: DeferredBlock<FixGas> = REGISTRY.registerBlock("fix_gas") { properties ->
        FixGas(properties
            .replaceable()
            .noCollission()
            .noLootTable()
            .air()
            .noOcclusion()
        )
    }

    val FIX_GAS_SPRAYER: DeferredBlock<FixGasSprayer> = REGISTRY.registerBlock("fix_gas_sprayer") { properties ->
        FixGasSprayer(
            properties
                .strength(2.2f,4f)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops()
        )
    }

    //TODO: Сделать герметичные блоки и дверь.

    val HERMETIC_WALL: DeferredBlock<Block> = REGISTRY.registerBlock("hermetic_wall") { properties ->
        Block(
            properties
                .strength(2f,7f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE)
        )
    }

    val HERMETIC_DOOR: DeferredBlock<Block> = REGISTRY.registerBlock("hermetic_door") { properties ->
        DoorBlock(
            BlockSetType.STONE,
            properties
                .strength(2f,7f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE)
                .noOcclusion()
        )
    }

    val HERMETIC_GLASS: DeferredBlock<Block> = REGISTRY.registerBlock("hermetic_glass") { properties ->
        TransparentBlock(
            properties
                .strength(0.4f,5.5f)
                .sound(SoundType.GLASS)
                .noOcclusion()
        )
    }

//    val GLITCH_FLUID_BLOCK: DeferredBlock<LiquidBlock> = REGISTRY.registerBlock("glitch_fluid_block") { properties ->
//        LiquidBlock(
//            ModFluids.GLITCH_FLUID.get(),
//            properties
//                .replaceable()
//                .noCollission()
//                .strength(100.0F)
//                .pushReaction(PushReaction.DESTROY)
//                .noLootTable()
//                .liquid()
//                .sound(SoundType.EMPTY)
//        )
//    }
}