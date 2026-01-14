package com.tech7fox.myolink.processor.classifier

import com.tech7fox.myolink.processor.DataPacket

/**
 * https://github.com/thalmiclabs/myo-bluetooth/blob/master/myohw.h#L345
 */
open class ClassifierEvent(packet: DataPacket, val type: Type) : DataPacket(packet.deviceAddress, packet.timeStamp) {
    /**
     * Types of classifier events.
     */
    enum class Type(val value: Byte) {
        ARM_SYNCED(0x01.toByte()),
        ARM_UNSYNCED(0x02.toByte()),
        POSE(0x03.toByte()),
        UNLOCKED(0x04.toByte()),
        LOCKED(0x05.toByte()),
        SYNC_FAILED(0x06.toByte()),
        WARM_UP_RESULT(0x07.toByte())
    }
}
