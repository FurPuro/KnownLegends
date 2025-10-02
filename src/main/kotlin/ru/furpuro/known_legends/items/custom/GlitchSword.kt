package ru.furpuro.known_legends.items.custom

import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.ToolMaterial
import ru.furpuro.known_legends.effects.ModMobEffects

class GlitchSword(material: ToolMaterial,attackDamage: Float, attackSpeed: Float,properties: Properties) : Item(properties.sword(material, attackDamage, attackSpeed)) {
    override fun postHurtEnemy(stack: ItemStack, target: LivingEntity, attacker: LivingEntity) {
        var effect = MobEffectInstance(ModMobEffects.GLITCH,30,0,false,false,true)
        target.addEffect(effect)

        super.postHurtEnemy(stack, target, attacker)
    }
}