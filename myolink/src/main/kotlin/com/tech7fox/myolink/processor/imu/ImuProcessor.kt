/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.processor.imu

import com.tech7fox.myolink.processor.BaseDataPacket
import com.tech7fox.myolink.processor.BaseProcessor
import com.tech7fox.myolink.services.Imu

/**
 * Processor that converts [BaseDataPacket] object into [ImuData] objects.
 */
class ImuProcessor : BaseProcessor() {

    init {
        subscriptions.add(Imu.IMUDATA.characteristicUUID)
    }

    override fun doProcess(packet: BaseDataPacket) {
        val imuData = ImuData(packet)

        for (listener in getDataListeners()) {
            val imuListener = listener as ImuDataListener
            imuListener.onNewImuData(imuData)
        }
    }

    interface ImuDataListener : DataListener {
        fun onNewImuData(imuData: ImuData)
    }

    fun addListener(listener: ImuDataListener) {
        super.addDataListener(listener)
    }

    companion object {
        private const val TAG = "myolink:ImuProcessor"
    }
}
