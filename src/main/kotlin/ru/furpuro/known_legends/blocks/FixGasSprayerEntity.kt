package ru.furpuro.known_legends.blocks

import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class FixGasSprayerEntity(pos: BlockPos, state: BlockState)
    : BlockEntity(ModBlockEntities.FIX_GAS_SPRAYER.get(), pos, state) {

    var GAS: Int = 0
    val MAX_GAS = 240000
    val GAS_CONSUME = 60

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)
        GAS = tag.getInt("Gas").orElse(0)
    }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)
        tag.putInt("Gas", GAS)
    }
}