/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.processor.emg

import com.tech7fox.myolink.processor.BaseDataPacket
import com.tech7fox.myolink.processor.BaseProcessor
import com.tech7fox.myolink.services.Emg

/**
 * Creates [EmgData] objects from [BaseDataPacket] objects.
 * More specifically, two for each.
 */
class EmgProcessor : BaseProcessor() {
    
    init {
        subscriptions.add(Emg.EMGDATA0.characteristicUUID)
        subscriptions.add(Emg.EMGDATA1.characteristicUUID)
        subscriptions.add(Emg.EMGDATA2.characteristicUUID)
        subscriptions.add(Emg.EMGDATA3.characteristicUUID)
    }

    override fun doProcess(packet: BaseDataPacket) {
        val data1 = ByteArray(8)
        System.arraycopy(packet.data, 0, data1, 0, 8)
        val emgData1 = EmgData(packet.deviceAddress, packet.timeStamp, data1)

        val data2 = ByteArray(8)
        System.arraycopy(packet.data, 8, data2, 0, 8)
        val emgData2 = EmgData(packet.deviceAddress, packet.timeStamp + 5, data2)

        for (listener in getDataListeners()) {
            val emgListener = listener as EmgDataListener
            emgListener.onNewEmgData(emgData1)
            emgListener.onNewEmgData(emgData2)
        }
    }

    interface EmgDataListener : DataListener {
        fun onNewEmgData(emgData: EmgData)
    }

    fun addListener(listener: EmgDataListener) {
        super.addDataListener(listener)
    }

    companion object {
        private const val TAG = "myolink:EmgProcessor"
    }
}
