package ru.furpuro.known_legends.blocks.entity

import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class FixGasSprayerEntity(pos:BlockPos,state:BlockState) : BlockEntity(ModBlockEntities.FIX_GAS_SPRAYER_BE.get(),pos,state) {
    var gas: Int = 0
    val maxGas: Int = 48000
    val gasConsume: Int = 10

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)
        tag.putInt("gas",gas)
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)
        gas = tag.getInt("gas").orElse(0)
    }
}