package com.tech7fox.myolink.processor.classifier

import com.tech7fox.myolink.processor.BaseDataPacket
import com.tech7fox.myolink.tools.ByteHelper

class ArmSyncedClassifierEvent(packet: BaseDataPacket) : ClassifierEvent(packet, Type.ARM_SYNCED) {
    /**
     * Possible warm-up states for Myo.
     */
    enum class WarmUpState(val value: Byte) {
        UNKNOWN(0x00.toByte()),
        COLD(0x01.toByte()),
        WARM(0x02.toByte())
    }

    var warmUpState: WarmUpState = WarmUpState.UNKNOWN
        private set

    /**
     * Enumeration identifying a right arm or left arm.
     */
    enum class Arm(val value: Byte) {
        UNKNOWN(0xFF.toByte()),
        RIGHT(0x01.toByte()),
        LEFT(0x02.toByte())
    }

    var arm: Arm = Arm.UNKNOWN
        private set

    /**
     * Possible directions for Myo's +x axis relative to a user's arm.
     */
    enum class Direction(val value: Byte) {
        UNKNOWN(0xFF.toByte()),
        TOWARDS_WRIST(0x01.toByte()),
        TOWARDS_ELBOW(0x02.toByte())
    }

    var direction: Direction = Direction.UNKNOWN
        private set
        
    private var mRotation: Float = 0f

    init {
        val byteHelper = ByteHelper(packet.data!!)
        val typeValue = byteHelper.getUInt8()
        require(type.value.toInt() == typeValue) { "Incompatible BaseDataPacket:$typeValue" }

        val armValue = byteHelper.getUInt8()
        for (arm in Arm.values()) {
            if (arm.value.toInt() == armValue) {
                this.arm = arm
                break
            }
        }
        
        val directionValue = byteHelper.getUInt8()
        for (direction in Direction.values()) {
            if (direction.value.toInt() == directionValue) {
                this.direction = direction
                break
            }
        }
        
        // FIXME what is the correct scale for this?
        // https://github.com/logotype/myodaemon/blob/master/native-osx/libs/myo.framework/Versions/A/Headers/cxx/impl/Hub_impl.hpp#L144
        if (packet.data.size > 3) {
            val warmUpStateValue = byteHelper.getUInt8()
            for (warmUpState in WarmUpState.values()) {
                if (warmUpState.value.toInt() == warmUpStateValue) {
                    this.warmUpState = warmUpState
                    break
                }
            }
            mRotation = byteHelper.getUInt16() / 16384.0f
        }
    }
}
