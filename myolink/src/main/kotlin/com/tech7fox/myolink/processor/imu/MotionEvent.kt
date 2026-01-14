package com.tech7fox.myolink.processor.imu

import com.tech7fox.myolink.processor.DataPacket

open class MotionEvent(packet: DataPacket, val type: Type) : DataPacket(packet) {
    /**
     * Types of motion events.
     */
    enum class Type(val value: Byte) {
        TAP(0x00.toByte())
    }
}
