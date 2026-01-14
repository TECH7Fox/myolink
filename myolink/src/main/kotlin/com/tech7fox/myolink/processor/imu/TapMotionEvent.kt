package com.tech7fox.myolink.processor.imu

import com.tech7fox.myolink.processor.DataPacket

class TapMotionEvent(packet: DataPacket) : MotionEvent(packet, Type.TAP) {
    var tapCount: Int = 0
}
