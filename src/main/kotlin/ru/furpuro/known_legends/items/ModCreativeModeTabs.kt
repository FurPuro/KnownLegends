package ru.furpuro.known_legends.items

import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import ru.furpuro.known_legends.Known_legends

object ModCreativeModeTabs {
    val CREATIVE_MODE_TAB: DeferredRegister<CreativeModeTab> = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,Known_legends.ID)

    val GLITCH_MODE_TAB: DeferredHolder<CreativeModeTab, CreativeModeTab> = CREATIVE_MODE_TAB.register("glitch_tab") { ->
        CreativeModeTab.builder().icon { ItemStack(ModItems.GLITCH_BLOCK.get()) }
            .title(Component.translatable("creativetab.known_legends.glitch"))
            .displayItems {itemDisplayParameters,output ->
                output.accept(ModItems.GLITCH_BLOCK)
                output.accept(ModItems.GLITCH_BLOCK_DECOR)
                output.accept(ModItems.GLITCH_STAIRS)
                output.accept(ModItems.GLITCH_SLAB)
                output.accept(ModItems.GLITCH_FENCE)
                output.accept(ModItems.GLITCH_LEAVES)
                output.accept(ModItems.GLITCH_DIRT)
                output.accept(ModItems.GLITCH_GRASS)
                output.accept(ModItems.GLITCH_STONE)
                output.accept(ModItems.GLITCH_COBBLESTONE)
                output.accept(ModItems.GLITCH_LOG)
                output.accept(ModItems.GLITCH_PLANKS)
                output.accept(ModItems.GLITCH_SAND)
                output.accept(ModItems.GLITCH_GRAVEL)
                output.accept(ModItems.GLITCH_GLASS)
                output.accept(ModItems.GLITCH_GLASS_PANE)
            }
            .build()
    }

    val DEFENSE_MODE_TAB: DeferredHolder<CreativeModeTab, CreativeModeTab> = CREATIVE_MODE_TAB.register("defense_tab") { ->
        CreativeModeTab.builder().icon { ItemStack(ModItems.HERMETIC_WALL.get()) }
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(Known_legends.ID,"glitch_tab"))
            .title(Component.translatable("creativetab.known_legends.defense"))
            .displayItems {itemDisplayParameters,output ->
                output.accept(ModItems.FIX_POWDER)
                output.accept(ModItems.FIX_GAS_SPRAYER)

                output.accept(ModItems.HERMETIC_WALL)
                output.accept(ModItems.HERMETIC_DOOR)
                output.accept(ModItems.HERMETIC_GLASS)

                output.accept(ModItems.PROTECTIVE_BOOTS)
                output.accept(ModItems.GAS_MASK)
            }
            .build()
    }
}