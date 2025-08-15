package ru.furpuro.known_legends.entities

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState
import software.bernie.geckolib.constant.dataticket.DataTicket
import software.bernie.geckolib.renderer.base.GeoRenderState

class ModRenderState : LivingEntityRenderState(), GeoRenderState {
    override fun <D : Any?> addGeckolibData(dataTicket: DataTicket<D>?, data: D?) {
        TODO("Not yet implemented")
    }

    override fun hasGeckolibData(dataTicket: DataTicket<*>?): Boolean {
        TODO("Not yet implemented")
    }

    override fun <D : Any?> getGeckolibData(dataTicket: DataTicket<D>?): D? {
        TODO("Not yet implemented")
    }

    override fun getDataMap(): MutableMap<DataTicket<*>, Any> {
        TODO("Not yet implemented")
    }
}