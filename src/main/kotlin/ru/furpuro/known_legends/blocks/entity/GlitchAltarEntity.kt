package ru.furpuro.known_legends.blocks.entity

import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.Containers
import net.minecraft.world.SimpleContainer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.items.ItemStackHandler
import ru.furpuro.known_legends.entities.glitch_entity.GlitchEntity
import software.bernie.geckolib.animatable.GeoBlockEntity
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animatable.manager.AnimatableManager
import software.bernie.geckolib.animatable.processing.AnimationController
import software.bernie.geckolib.animatable.processing.AnimationTest
import software.bernie.geckolib.animation.PlayState
import software.bernie.geckolib.animation.RawAnimation
import software.bernie.geckolib.util.GeckoLibUtil
import javax.annotation.Nullable


class GlitchAltarEntity(pos: BlockPos,state: BlockState) : BlockEntity(ModBlockEntities.GLITCH_ALTAR.get(),pos,state), GeoBlockEntity {
    private val cache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    val IDLE_ANIM: RawAnimation = RawAnimation.begin().thenLoop("animation.glitch_altar.idle")

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
        controllers.add(AnimationController("idle", 5, ::idleController))
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return cache
    }

    private fun <E : GlitchAltarEntity?> idleController(animTest: AnimationTest<E>): PlayState {
        return animTest.setAndContinue(IDLE_ANIM)
    }

    val inventory: ItemStackHandler = object : ItemStackHandler(1) {
        override fun getStackLimit(slot: Int, stack: ItemStack): Int {
            return 1
        }

        override fun onContentsChanged(slot: Int) {
            setChanged()
            if (!level!!.isClientSide()) {
                level!!.sendBlockUpdated(blockPos, blockState, blockState, 3)
            }
        }
    }

    private var rotation = 0f
    var defaultRotationSpeed  = 1.5f
    var rotationSpeed = 1.5f
    var progress = 0f

    fun getRenderingRotation(): Float {
        rotation += rotationSpeed
        if (rotation >= 360f) {
            rotation = 0f
        }
        return rotation
    }

    fun clearContents() {
        inventory.setStackInSlot(0, ItemStack.EMPTY)
    }

/*    fun drops() {
        val inv = SimpleContainer(inventory.slots)
        for (i in 0 until inventory.slots) {
            inv.setItem(i, inventory.getStackInSlot(i))
        }

        Containers.dropContents(level!!, worldPosition, inv)
    }*/

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)
        tag.put("inventory", inventory.serializeNBT(registries))
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)
        inventory.deserializeNBT(registries, tag.getCompound("inventory").get())
    }

    @Nullable
    override fun getUpdatePacket(): Packet<ClientGamePacketListener> {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag {
        return saveWithoutMetadata(pRegistries)
    }
}