package ru.furpuro.known_legends.entities

import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.level.Level


class GlitchEntity(type: EntityType<GlitchEntity>, level: Level) : Monster(type, level) {
    val DATA: EntityDataAccessor<Int> = SynchedEntityData.defineId(
        GlitchEntity::class.java,
        EntityDataSerializers.INT
    )

    companion object {
        
    }

    override fun readAdditionalSaveData(compoundTag: CompoundTag) {

    }

    override fun addAdditionalSaveData(compoundTag: CompoundTag) {

    }

    override fun defineSynchedData(builder: SynchedEntityData.Builder) {
        builder.define(DATA,0)
    }

    override fun hurtServer(level: ServerLevel, damageSource: DamageSource, amount: Float): Boolean {
        return true
    }

    override fun isPickable(): Boolean = false
}