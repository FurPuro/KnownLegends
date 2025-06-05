package ru.furpuro.known_legends.blocks

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.InsideBlockEffectApplier
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.FenceBlock
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.StairBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.Half
import net.minecraft.world.level.block.state.properties.StairsShape
import net.minecraft.world.ticks.TickPriority
import ru.furpuro.known_legends.custom.Functions.glitchBlockStep
import ru.furpuro.known_legends.custom.Functions.spreadGlitch
import ru.furpuro.known_legends.data.ModAttachments
import ru.furpuro.known_legends.items.ModItems

open class GlitchFenceBlock(props: Properties) : FenceBlock(props.randomTicks()) {
    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, movedByPiston: Boolean) {
        if (!level.isClientSide()) {
            val data = level.getData(ModAttachments.POINTS_DATA)
            data.points++
        }

        super.onPlace(state, level, pos, oldState, movedByPiston)
    }

    override fun stepOn(level: Level, pos: BlockPos, state: BlockState, entity: Entity) {
        if (!level.isClientSide()) {
            glitchBlockStep(entity,level)
        }

        super.stepOn(level, pos, state, entity)
    }

    public override fun randomTick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (!level.isClientSide()) {
            spreadGlitch(pos,level,random)
        }

        super.randomTick(state, level, pos, random)
    }
}