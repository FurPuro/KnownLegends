package ru.furpuro.known_legends.data

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.nbt.CompoundTag

class PointsData(var points: Int = 0) {
    companion object {
        val CODEC: Codec<PointsData> = RecordCodecBuilder.create { instance ->
            instance.group(
                Codec.INT.fieldOf("points").forGetter { it.points }
            ).apply(instance, ::PointsData)
        }
    }
}