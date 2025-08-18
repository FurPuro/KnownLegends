package ru.furpuro.known_legends.blocks

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import net.minecraft.world.ticks.TickPriority
import ru.furpuro.known_legends.custom.Functions.getPhase
import ru.furpuro.known_legends.custom.Functions.spreadGlitch
import ru.furpuro.known_legends.data.ModAttachments
import ru.furpuro.known_legends.effects.ModMobEffects
import ru.furpuro.known_legends.items.ModItems

class GlitchAir(props: Properties) : Block(props.randomTicks().air().noOcclusion().noLootTable().noCollission().replaceable()) {
    override fun skipRendering(state: BlockState, adjacentState: BlockState, side: Direction): Boolean {
        return adjacentState.`is`(this)
    }

    override fun getLightBlock(state: BlockState): Int = 0

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return Shapes.empty()
    }

    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, movedByPiston: Boolean) {
        if (!level.isClientSide()) {
            level.scheduleTick(pos, this, 10, TickPriority.NORMAL)
            val data = level.getData(ModAttachments.POINTS_DATA)
            if (getPhase(data.points) == 0) {
                data.points++
            }
        }

        super.onPlace(state, level, pos, oldState, movedByPiston)
    }

    public override fun randomTick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (!level.isClientSide()) {
            spreadGlitch(pos,level,random,true)
        }
        super.randomTick(state, level, pos, random)
    }

    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (!level.isClientSide()) {
            if (1 >= level.random.nextIntBetweenInclusive(1,100)) {
                level.sendParticles(
                    ParticleTypes.GLOW,
                    pos.x.toDouble()+0.5,
                    pos.y.toDouble()+0.5,
                    pos.z.toDouble()+0.5,
                    1,
                    0.5,
                    0.5,
                    0.5,
                    0.1
                )
            }

            val entities = level.getEntitiesOfClass(LivingEntity::class.java, AABB(pos))
            for (entity in entities) {
                if (!BuiltInRegistries.ENTITY_TYPE.getKey(entity.type).toString().contains("glitch")) {
                    if (entity.getItemBySlot(EquipmentSlot.HEAD).item != ModItems.GAS_MASK.get()) {
                        val effect = MobEffectInstance(MobEffects.HUNGER, 40, 1, false, false, true)
                        entity.addEffect(effect)
                        val effect2 = MobEffectInstance(MobEffects.WITHER, 40, 1, false, false,true)
                        entity.addEffect(effect2)
                    } else if (25 >= random.nextIntBetweenInclusive(1,100)) {
                        entity.getItemBySlot(EquipmentSlot.HEAD).hurtAndBreak(
                            1,
                            entity,
                            EquipmentSlot.HEAD
                        )
                    }
                }
            }
            level.scheduleTick(pos, this, 10, TickPriority.NORMAL)
        }
        super.tick(state, level, pos, random)
    }
}