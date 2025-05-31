package ru.furpuro.known_legends.data

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.world.level.Level
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.attachment.AttachmentType
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries
import ru.furpuro.known_legends.Known_legends.ID
import java.util.function.Supplier

object ModAttachments {
    val POINTS_DATA = AttachmentType.builder<PointsData>(Supplier { PointsData() })
        .serialize(PointsData.CODEC)
        .build()

    private val ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ID)

    fun register(bus: IEventBus) {
        ATTACHMENTS.register("points_data") { -> POINTS_DATA }
        ATTACHMENTS.register(bus)
    }
}