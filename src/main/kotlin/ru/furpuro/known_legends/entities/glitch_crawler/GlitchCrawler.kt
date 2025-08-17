package ru.furpuro.known_legends.entities.glitch_crawler

import net.minecraft.server.level.ServerLevel
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.goal.FloatGoal
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.entity.npc.AbstractVillager
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import ru.furpuro.known_legends.effects.ModMobEffects
import software.bernie.geckolib.animatable.GeoEntity
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animatable.manager.AnimatableManager.ControllerRegistrar
import software.bernie.geckolib.animatable.processing.AnimationController
import software.bernie.geckolib.animatable.processing.AnimationTest
import software.bernie.geckolib.animation.PlayState
import software.bernie.geckolib.animation.RawAnimation
import software.bernie.geckolib.util.GeckoLibUtil


open class GlitchCrawler(type: EntityType<out GlitchCrawler?>?, level: Level?) : Monster(type!!, level!!), GeoEntity {
    companion object {
        protected val IDLE_ANIM: RawAnimation = RawAnimation.begin().thenLoop("animation.glitch_crawler.idle")
        protected val WALK_ANIM: RawAnimation = RawAnimation.begin().thenLoop("animation.glitch_crawler.walk")
    }

    private val geoCache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    @Override
    override fun registerGoals() {
        goalSelector.addGoal(1, FloatGoal(this));
        goalSelector.addGoal(4, MeleeAttackGoal(this, 1.0, false))
        goalSelector.addGoal(6, LookAtPlayerGoal(this, Player::class.java, 8.0f))
        goalSelector.addGoal(6, RandomLookAroundGoal(this))
        targetSelector.addGoal(1, HurtByTargetGoal(this).setAlertOthers(this::class.java))
        targetSelector.addGoal(2, NearestAttackableTargetGoal(this, Player::class.java, true))
        targetSelector.addGoal(2, NearestAttackableTargetGoal(this, AbstractVillager::class.java, true))
    }

    override fun registerControllers(controllers: ControllerRegistrar) {
        controllers.add(AnimationController("idle_walk",3,::idleWalkAnimController))
    }

    override fun doHurtTarget(p_376642_: ServerLevel, p_21372_: Entity): Boolean {
        if (p_21372_ is LivingEntity) {
            val effect = MobEffectInstance(ModMobEffects.GLITCH, 200, 1, false, false, true)
            p_21372_.addEffect(effect)
        }

        return super.doHurtTarget(p_376642_, p_21372_)
    }

    override fun onAddedToLevel() {
        val effect = MobEffectInstance(ModMobEffects.GLITCH, 9999, 2, false, false, true)
        this.addEffect(effect)
    }

    private fun <E : GlitchCrawler?> idleWalkAnimController(animTest: AnimationTest<E>): PlayState {
        if (animTest.isMoving) return animTest.setAndContinue(WALK_ANIM)

        return animTest.setAndContinue(IDLE_ANIM)
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return this.geoCache
    }
}