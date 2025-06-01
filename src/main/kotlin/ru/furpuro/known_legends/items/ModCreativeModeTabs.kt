package ru.furpuro.known_legends.items

import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.registries.DeferredRegister
import ru.furpuro.known_legends.Known_legends

object ModCreativeModeTabs {
    val CREATIVE_MODE_TAB: DeferredRegister<CreativeModeTab> = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,Known_legends.ID)

    val KNOWN_LEGENDS_MODE_TAB = CREATIVE_MODE_TAB.register("known_legends_tab") { ->
        CreativeModeTab.builder().icon { ItemStack(ModItems.FIX_POWDER.get()) }
            .title(Component.translatable("creativetab.known_legends.known_legends"))
            .displayItems {itemDisplayParameters,output ->
                output.accept(ModItems.GLITCH_BLOCK)
                output.accept(ModItems.GLITCH_BLOCK_DECOR)
                output.accept(ModItems.GLITCH_LEAVES)

                output.accept(ModItems.FIX_POWDER)
                output.accept(ModItems.FIX_GAS_SPRAYER)

                output.accept(ModItems.PROTECTIVE_BOOTS)
                output.accept(ModItems.GAS_MASK)
            }
            .build()
    }
}