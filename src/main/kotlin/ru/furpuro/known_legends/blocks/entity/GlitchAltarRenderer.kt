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

        poseStack.pushPose()
        poseStack.translate(0.5,0.8,0.5)
        var scale = 0.2f
        poseStack.scale(scale,scale,scale)
        poseStack.mulPose(Axis.YP.rotationDegrees(animatable!!.getRenderingRotation()))
        for (i in 0..<animatable.inventory.slots) {

            val stack: ItemStack = animatable.inventory.getStackInSlot(i)

            if (stack.isEmpty)
                continue

            if (i == 0 && animatable.inventory.getStackInSlot(1).isEmpty) {
                poseStack.scale(2.75f,2.75f,2.75f)
                poseStack.translate(0.0,0.6,0.0)
            }

            itemRenderer.renderStatic(
                stack, ItemDisplayContext.FIXED, getLightLevel(
                    animatable.level!!,
                    animatable.blockPos
                ), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, animatable.level, 1
            )

            if (i == 0 && animatable.inventory.getStackInSlot(1).isEmpty) {
                break
            }

            poseStack.translate(0.0,1.0,0.0)
        }
        poseStack.popPose()


        super.render(animatable, partialTick, poseStack, bufferSource, packedLight, packedOverlay, cameraPosition)
    }
    private fun getLightLevel(level: Level, pos: BlockPos): Int {
        val bLight: Int = level.getBrightness(LightLayer.BLOCK, pos)
        val sLight: Int = level.getBrightness(LightLayer.SKY, pos)
        return LightTexture.pack(bLight, sLight)
    }
}