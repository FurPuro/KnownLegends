package ru.furpuro.known_legends.custom

import com.google.common.collect.Maps
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.item.equipment.*
import net.neoforged.neoforge.common.Tags
import ru.furpuro.known_legends.Known_legends.ID

object ModMaterials {
     val PROTECTIVE_SUIT: ArmorMaterial = ArmorMaterial(
        31,
        makeDefense(5, 0, 0, 4, 0),
        9,
        SoundEvents.ARMOR_EQUIP_LEATHER,
        0.0f,
        0.0f,
        Tags.Items.OBSIDIANS_NORMAL,
        ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(ID, "protective_suit"))
    )

    private fun makeDefense(boots: Int, leggings: Int, chestplate: Int, helmet: Int, body: Int): Map<ArmorType, Int> {
        return Maps.newEnumMap(
            java.util.Map.of(
                ArmorType.BOOTS,
                boots,
                ArmorType.LEGGINGS,
                leggings,
                ArmorType.CHESTPLATE,
                chestplate,
                ArmorType.HELMET,
                helmet,
                ArmorType.BODY,
                body
            )
        )
    }
}