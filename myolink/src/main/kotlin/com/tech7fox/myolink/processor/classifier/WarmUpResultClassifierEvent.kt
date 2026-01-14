package com.tech7fox.myolink.processor.classifier

import com.tech7fox.myolink.processor.BaseDataPacket
import com.tech7fox.myolink.tools.ByteHelper

class WarmUpResultClassifierEvent(packet: BaseDataPacket) : ClassifierEvent(packet, Type.WARM_UP_RESULT) {
    /**
     * Possible warm-up results for Myo.
     */
    enum class WarmUpResult(val value: Byte) {
        UNKNOWN(0x00.toByte()),
        SUCCESS(0x01.toByte()),
        FAILED_TIMEOUT(0x02.toByte())
    }

    var warmUpResult: WarmUpResult? = null

    init {
        val byteHelper = ByteHelper(packet.data!!)
        val typeValue = byteHelper.getUInt8()
        require(type.value.toInt() == typeValue) { "Incompatible BaseDataPacket:$typeValue" }

        val warmUpResultValue = byteHelper.getUInt8()
        for (warmUpResult in WarmUpResult.values()) {
            if (warmUpResult.value.toInt() == warmUpResultValue) {
                this.warmUpResult = warmUpResult
                break
            }
        }
    }
}
