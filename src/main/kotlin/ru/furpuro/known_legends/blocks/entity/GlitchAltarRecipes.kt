package ru.furpuro.known_legends.blocks.entity

import net.minecraft.world.item.Items
import ru.furpuro.known_legends.items.ModItems

object GlitchAltarRecipes {
    val GLITCH_SWORD_RECIPE = listOf(
        Items.DIAMOND_SWORD,
        ModItems.GLITCH_SHARD.get(),
        ModItems.GLITCH_SHARD.get(),
        ModItems.GLITCH_SHARD.get()
    )
}