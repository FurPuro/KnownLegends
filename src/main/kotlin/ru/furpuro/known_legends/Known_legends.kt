package ru.furpuro.known_legends

import net.minecraft.client.Minecraft
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.FireBlock
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import net.neoforged.neoforge.common.NeoForge
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import ru.furpuro.known_legends.blocks.ModBlocks
import ru.furpuro.known_legends.blocks.entity.ModBlockEntities
import ru.furpuro.known_legends.custom.EventHandler
import ru.furpuro.known_legends.data.ModAttachments
import ru.furpuro.known_legends.fuids.ModFluids
import ru.furpuro.known_legends.items.ModCreativeModeTabs
import ru.furpuro.known_legends.items.ModItems
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.neoforge.forge.runForDist

/**
 * Main mod class.
 *
 * An example for blocks is in the `blocks` package of this mod.
 */
@Mod(Known_legends.ID)
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
object Known_legends {
    const val ID = "known_legends"

    // the logger for our mod
    val LOGGER: Logger = LogManager.getLogger(ID)

    init {
        LOGGER.log(Level.INFO, "Hello world!")

        // Register the KDeferredRegister to the mod-specific event bus
        ModBlocks.REGISTRY.register(MOD_BUS)
        ModItems.REGISTRY.register(MOD_BUS)
        ModAttachments.register(MOD_BUS)
        NeoForge.EVENT_BUS.register(EventHandler())
        ModFluids.FLUIDS.register(MOD_BUS)
        ModFluids.FLUID_TYPES.register(MOD_BUS)
        ModBlockEntities.BLOCK_ENTITIES.register(MOD_BUS)
        ModCreativeModeTabs.CREATIVE_MODE_TAB.register(MOD_BUS)

        val obj = runForDist(
            clientTarget = {
                MOD_BUS.addListener(::onClientSetup)
                Minecraft.getInstance()
            },
            serverTarget = {
                MOD_BUS.addListener(::onServerSetup)
                "test"
            })

        println(obj)
    }

    /**
     * This is used for initializing client specific
     * things such as renderers and keymaps
     * Fired on the mod specific event bus.
     */
    private fun onClientSetup(event: FMLClientSetupEvent) {
        LOGGER.log(Level.INFO, "Initializing client...")

        //ItemBlockRenderTypes.setRenderLayer(ModFluids.GLITCH_FLUID.get(), RenderType.translucent())
        //ItemBlockRenderTypes.setRenderLayer(ModFluids.GLITCH_FLUID_FLOW.get(), RenderType.translucent())
    }

    //@SubscribeEvent
    //private fun onRegisterClientExtensions(event: RegisterClientExtensionsEvent) {
//        LOGGER.log(Level.INFO,"Initializing client extensions")
//
//        event.registerFluidType(object : IClientFluidTypeExtensions {
//            private val GLITCH_FLUID_STILL: ResourceLocation =
//                ResourceLocation.withDefaultNamespace("block/glitch_fluid_still")
//            private val GLITCH_FLUID_FLOW: ResourceLocation =
//                ResourceLocation.withDefaultNamespace("block/glitch_fluid_flow")
//
//            @Override
//            override fun getStillTexture(): ResourceLocation {
//                return GLITCH_FLUID_STILL
//            }
//
//            @Override
//            override fun getFlowingTexture(): ResourceLocation {
//                return GLITCH_FLUID_FLOW
//            }
//
//            @Override
//            override fun getTintColor(): Int {
//                return 0x8354b3
//            }
//
//        }, ModFluids.GLITCH_FLUID_TYPE.get())
//    }

    /**
     * Fired on the global Forge bus.
     */
    private fun onServerSetup(event: FMLDedicatedServerSetupEvent) {
        LOGGER.log(Level.INFO, "Server starting...")
    }

    @SubscribeEvent
    private fun onCommonSetup(event: FMLCommonSetupEvent) {
        LOGGER.log(Level.INFO, "Hello! This is working!")

        val fireBlock = Blocks.FIRE as FireBlock
        fireBlock.setFlammable(ModBlocks.GLITCH_BLOCK.get(), 20, 25)
        fireBlock.setFlammable(ModBlocks.GLITCH_DIRT.get(), 18, 23)
        fireBlock.setFlammable(ModBlocks.GLITCH_STONE.get(), 15, 20)
        fireBlock.setFlammable(ModBlocks.GLITCH_LOG.get(), 22, 27)
        fireBlock.setFlammable(ModBlocks.GLITCH_LEAVES.get(), 40, 45)
    }
}
