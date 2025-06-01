package ru.furpuro.known_legends.blocks

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredRegister
import ru.furpuro.known_legends.Known_legends

object ModBlocks {
    val REGISTRY: DeferredRegister.Blocks = DeferredRegister.createBlocks(Known_legends.ID)

    val GLITCH_BLOCK_DECOR: DeferredBlock<Block> = REGISTRY.registerBlock("glitch_block_decor") { properties ->
        Block(properties
            .strength(1.2f,1f)
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

    val GLITCH_LEAVES: DeferredBlock<GlitchLeaves> = REGISTRY.registerBlock("glitch_leaves") { properties ->
        GlitchLeaves(properties
            .strength(0.2f,1000f)
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
                .strength(1.2f)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops()
        )
    }

    //TODO: Сделать герметичные блок и дверь.

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