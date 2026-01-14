package com.tech7fox.myolink.processor.classifier

import com.tech7fox.myolink.processor.BaseDataPacket
import com.tech7fox.myolink.tools.ByteHelper

class SyncFailedClassifierEvent(packet: BaseDataPacket) : ClassifierEvent(packet, Type.SYNC_FAILED) {
    /**
     * Possible outcomes when the user attempts a sync gesture.
     */
    enum class SyncResult(val value: Byte) {
        /**
         * Sync gesture was performed too hard.
         */
        FAILED_TOO_HARD(0x01.toByte())
    }

    var syncResult: SyncResult? = null
        private set

    init {
        val byteHelper = ByteHelper(packet.data!!)
        val typeValue = byteHelper.getUInt8()
        require(type.value.toInt() == typeValue) { "Incompatible BaseDataPacket:$typeValue" }

        val syncResultValue = byteHelper.getUInt8()
        for (syncResult in SyncResult.values()) {
            if (syncResult.value.toInt() == syncResultValue) {
                this.syncResult = syncResult
                break
            }
        }
    }
}
