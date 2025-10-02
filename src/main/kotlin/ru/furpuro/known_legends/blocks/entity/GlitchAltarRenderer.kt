package ru.furpuro.known_legends.blocks.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.core.BlockPos
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.LightLayer
import net.minecraft.world.phys.Vec3
import software.bernie.geckolib.renderer.GeoBlockRenderer


class GlitchAltarRenderer :
    GeoBlockRenderer<GlitchAltarEntity?>(GlitchAltarModel()) {
    override fun render(
        animatable: GlitchAltarEntity?,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int,
        cameraPosition: Vec3
    ) {
        val itemRenderer = Minecraft.getInstance().itemRenderer
        val stack: ItemStack = animatable!!.inventory.getStackInSlot(0)

        poseStack.pushPose()
        poseStack.translate(0.5f, 1f-animatable.progress, 0.5f)
        poseStack.scale(0.55f, 0.55f, 0.55f)
        poseStack.mulPose(Axis.YP.rotationDegrees(animatable.getRenderingRotation()))

        itemRenderer.renderStatic(
            stack, ItemDisplayContext.FIXED, getLightLevel(
                animatable.level!!,
                animatable.blockPos
            ), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, animatable.level, 1
        )
        poseStack.popPose()

        super.render(animatable, partialTick, poseStack, bufferSource, packedLight, packedOverlay, cameraPosition)
    }
    private fun getLightLevel(level: Level, pos: BlockPos): Int {
        val bLight: Int = level.getBrightness(LightLayer.BLOCK, pos)
        val sLight: Int = level.getBrightness(LightLayer.SKY, pos)
        return LightTexture.pack(bLight, sLight)
    }
    }