/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.processor.imu

import com.tech7fox.myolink.processor.BaseDataPacket
import com.tech7fox.myolink.processor.DataPacket
import com.tech7fox.myolink.tools.ByteHelper

/**
 * Class representing data from the Myo's IMU sensors.
 */
class ImuData(packet: BaseDataPacket) : DataPacket(packet.deviceAddress, packet.timeStamp) {
    /**
     * Values range form -1.0 to 1.0
     * Format: [w,x,y,z]
     *
     * @see <a href="https://github.com/thalmiclabs/myo-bluetooth/blob/master/myohw.h">Myo protocol specification</a>
     */
    val orientationData: DoubleArray
    
    /**
     * Values range from -1.0 to 1.0
     * Format: [?,?,?]
     *
     * @see <a href="https://github.com/thalmiclabs/myo-bluetooth/blob/master/myohw.h">Myo protocol specification</a>
     */
    val accelerometerData: DoubleArray
    
    /**
     * Values range from -? to ?
     * Format: [?,?,?]
     *
     * @see <a href="https://github.com/thalmiclabs/myo-bluetooth/blob/master/myohw.h">Myo protocol specification</a>
     */
    val gyroData: DoubleArray

    init {
        val byteHelper = ByteHelper(packet.data!!)
        
        orientationData = DoubleArray(4) { i ->
            byteHelper.getUInt16() / MYOHW_ORIENTATION_SCALE
        }

        accelerometerData = DoubleArray(3) { i ->
            byteHelper.getUInt16() / MYOHW_ACCELEROMETER_SCALE
        }

        gyroData = DoubleArray(3) { i ->
            byteHelper.getUInt16() / MYOHW_GYROSCOPE_SCALE
        }
    }

    companion object {
        private const val MYOHW_ORIENTATION_SCALE = 16384.0
        private const val MYOHW_ACCELEROMETER_SCALE = 2048.0
        private const val MYOHW_GYROSCOPE_SCALE = 16.0

        @JvmStatic
        fun format(data: DoubleArray): String {
            val builder = StringBuilder()
            for (d in data) {
                builder.append(String.format("%+.3f", d)).append(" ")
            }
            return builder.toString()
        }
    }
}
