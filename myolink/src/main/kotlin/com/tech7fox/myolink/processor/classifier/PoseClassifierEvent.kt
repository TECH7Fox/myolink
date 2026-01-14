package com.tech7fox.myolink.processor.classifier

import com.tech7fox.myolink.processor.BaseDataPacket
import com.tech7fox.myolink.tools.ByteHelper

class PoseClassifierEvent(packet: BaseDataPacket) : ClassifierEvent(packet, Type.POSE) {
    enum class Pose(val value: Short) {
        REST(0x0000.toShort()),
        FIST(0x0001.toShort()),
        WAVE_IN(0x0002.toShort()),
        WAVE_OUT(0x0003.toShort()),
        FINGERS_SPREAD(0x0004.toShort()),
        DOUBLE_TAP(0x0005.toShort()),
        UNKNOWN(0xFFFF.toShort())
    }

    var pose: Pose = Pose.UNKNOWN
        private set

    init {
        val byteHelper = ByteHelper(packet.data!!)
        val typeValue = byteHelper.getUInt8()
        require(type.value.toInt() == typeValue) { "Incompatible BaseDataPacket:$typeValue" }

        val poseValue = byteHelper.getUInt16()
        for (pose in Pose.values()) {
            if (pose.value.toInt() == poseValue) {
                this.pose = pose
                break
            }
        }
    }
}
