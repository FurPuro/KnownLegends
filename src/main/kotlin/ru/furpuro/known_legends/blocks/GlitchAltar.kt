package ru.furpuro.known_legends.blocks

import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import ru.furpuro.known_legends.Known_legends
import ru.furpuro.known_legends.blocks.entity.GlitchAltarEntity
import ru.furpuro.known_legends.blocks.entity.GlitchAltarRecipes
import ru.furpuro.known_legends.blocks.entity.GlitchAltarRenderer
import ru.furpuro.known_legends.blocks.entity.ModBlockEntities
import ru.furpuro.known_legends.items.ModItems


class GlitchAltar(props:Properties): BaseEntityBlock(props),EntityBlock {
    private fun mayPlaceOn(state: BlockState): Boolean {
        return BuiltInRegistries.BLOCK.getKey(state.block).toString().contains("glitch") && state.isSolidRender
    }

    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        val blockpos: BlockPos = pos.below()
        val belowBlockState: BlockState = level.getBlockState(blockpos)
        val soilDecision = belowBlockState.canSustainPlant(level, blockpos, Direction.UP, state)
        if (!soilDecision.isDefault) return soilDecision.isTrue
        return this.mayPlaceOn(belowBlockState)
    }

    override fun newBlockEntity(p0: BlockPos, p1: BlockState): BlockEntity? {
        return ModBlockEntities.GLITCH_ALTAR.get().create(p0,p1)
    }

    override fun codec(): MapCodec<out BaseEntityBlock> {
        TODO("Not yet implemented")
    }

    override fun propagatesSkylightDown(state: BlockState): Boolean = true
    override fun getLightBlock(state: BlockState): Int = 1

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return Block.box(1.0,0.0,1.0,15.0,11.0,15.0)
    }

    override fun useItemOn(
        stack: ItemStack,
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hitResult: BlockHitResult
    ): InteractionResult {
        if (level.getBlockEntity(pos) is GlitchAltarEntity) {
            val be = level.getBlockEntity(pos) as GlitchAltarEntity
            if (be.inventory.getStackInSlot(0).isEmpty && !stack.isEmpty) {
                be.inventory.insertItem(0, stack.copy(), false)
                stack.shrink(1)
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f)
            } else if (be.inventory.getStackInSlot(1).isEmpty && !stack.isEmpty) {
                be.inventory.insertItem(1, stack.copy(), false)
                stack.shrink(1)
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f)
            } else if (be.inventory.getStackInSlot(2).isEmpty && !stack.isEmpty) {
                be.inventory.insertItem(2, stack.copy(), false)
                stack.shrink(1)
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f)
            } else if (be.inventory.getStackInSlot(3).isEmpty && !stack.isEmpty) {
                be.inventory.insertItem(3, stack.copy(), false)
                stack.shrink(1)
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f)
            } else if (stack.isEmpty) {
                val stackOnPedestal: ItemStack = be.inventory.extractItem(3, 1, false)
                val stackOnPedestal1: ItemStack = be.inventory.extractItem(2, 1, false)
                val stackOnPedestal2: ItemStack = be.inventory.extractItem(1, 1, false)
                val stackOnPedestal3: ItemStack = be.inventory.extractItem(0, 1, false)
                player.addItem(stackOnPedestal)
                player.addItem(stackOnPedestal1)
                player.addItem(stackOnPedestal2)
                player.addItem(stackOnPedestal3)
                be.clearContents()
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f)
            }
        }

        return InteractionResult.SUCCESS
    }

    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, movedByPiston: Boolean) {
        level.scheduleTick(pos,asBlock(),2)

        super.onPlace(state, level, pos, oldState, movedByPiston)
    }

    private fun getCraftedItem(be:GlitchAltarEntity): ItemStack? {
        val invItems: MutableList<Item> = MutableList(4) { Items.AIR }

        for (i in 0..<be.inventory.slots) {
            invItems[i] = be.inventory.getStackInSlot(i).item
            println(invItems[i])
            println(GlitchAltarRecipes.GLITCH_SWORD_RECIPE[i])
        }

        if (GlitchAltarRecipes.GLITCH_SWORD_RECIPE.containsAll(invItems)) {
            println("yes")
            return ModItems.GLITCH_SWORD.toStack()
        }
        println("no")
        return null
    }

    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (level.getBlockEntity(pos) is GlitchAltarEntity) {
            val be = level.getBlockEntity(pos) as GlitchAltarEntity

            val craftedItem = getCraftedItem(be)

            if (craftedItem != null) {
                if (be.progress < 1.00f) {
                    be.progress += 0.01f
                    be.rotationSpeed += be.progress * 10f
                    level.sendParticles(
                        ParticleTypes.GLOW,
                        pos.x + 0.5,
                        pos.y + 0.85,
                        pos.z + 0.5,
                        1,
                        0.1,
                        0.2,
                        0.1,
                        be.progress.toDouble()
                    )
                }

                if (be.progress >= 1.00f) {
                    be.rotationSpeed = be.defaultRotationSpeed
                    be.progress = 0f
                    be.clearContents()
                    be.inventory.insertItem(0,craftedItem,false)
                }
            } else {
                be.progress = 0f
                be.rotationSpeed = be.defaultRotationSpeed
            }
        }

        level.scheduleTick(pos,asBlock(),2)

        super.tick(state, level, pos, random)
    }
}