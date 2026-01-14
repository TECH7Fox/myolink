/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.processor.emg

import com.tech7fox.myolink.processor.BaseDataPacket

/**
 * Class to hold EMG data from one of the Myo's 8 EMG sensors.
 */
class EmgData(
    val deviceAddress: String,
    /**
     * Timestamp of this EMG dataset. Based on [BaseDataPacket.timeStamp].
     * As every [BaseDataPacket] contains two EMG datasets.
     * The second EMG data step will have [BaseDataPacket.timeStamp]+5ms as timestamp.
     *
     * @return timestamp in miliseconds
     * @see <a href="http://developerblog.myo.com/myocraft-emg-in-the-bluetooth-protocol/">Myo blog: EMG data</a>
     */
    val timestamp: Long,
    /**
     * @return Array of byte values ranging from -128 to 127.
     * @see <a href="https://github.com/thalmiclabs/myo-bluetooth/blob/master/myohw.h#L371">Myo protocol specification</a>
     */
    val data: ByteArray
) {
    override fun toString(): String {
        val builder = StringBuilder()
        for (b in data) {
            builder.append(String.format("%+04d", b.toInt())).append(" ")
        }
        return builder.toString()
    }
}
