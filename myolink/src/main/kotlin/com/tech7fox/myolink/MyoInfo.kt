package com.tech7fox.myolink

import com.tech7fox.myolink.msgs.ReadMsg
import com.tech7fox.myolink.processor.classifier.PoseClassifierEvent
import com.tech7fox.myolink.tools.ByteHelper

class MyoInfo(msg: ReadMsg) {
    /**
     * Unique serial number of this Myo.
     */
    val serialNumber: IntArray

    /**
     * Pose that should be interpreted as the unlock pose.
     */
    val unlockPose: PoseClassifierEvent.Pose

    enum class ActiveClassifierType(val value: Byte) {
        BUILTIN(0x0.toByte()),
        CUSTOM(0x1.toByte())
    }

    /**
     * Whether Myo is currently using a built-in or a custom classifier.
     */
    val activeClassifierType: ActiveClassifierType

    /**
     * Index of the classifier that is currently active
     */
    val activeClassifierIndex: Int

    /**
     * Whether Myo contains a valid custom classifier. 1 if it does, otherwise 0.
     */
    val hasCustomClassifier: Boolean

    /**
     * Set if the Myo uses BLE indicates to stream data, for reliable capture.
     */
    val streamIndicating: Int

    enum class Sku(val value: Byte) {
        UNKNOWN(0x0.toByte()),
        BLACK(0x1.toByte()),
        WHITE(0x2.toByte())
    }

    /**
     * SKU value of the device. See myohw_sku_t
     */
    val sku: Sku

    val reservedData: ByteArray

    init {
        val byteHelper = ByteHelper(msg.value!!)
        
        serialNumber = IntArray(6) { byteHelper.getUInt8() }

        val unlockPoseValue = byteHelper.getUInt16()
        var tempUnlockPose: PoseClassifierEvent.Pose? = null
        for (pose in PoseClassifierEvent.Pose.values()) {
            if (pose.value.toInt() == unlockPoseValue) {
                tempUnlockPose = pose
                break
            }
        }
        unlockPose = tempUnlockPose ?: PoseClassifierEvent.Pose.UNKNOWN

        val classifierTypeValue = byteHelper.getUInt8()
        var tempClassifierType = ActiveClassifierType.BUILTIN
        for (type in ActiveClassifierType.values()) {
            if (type.value.toInt() == classifierTypeValue) {
                tempClassifierType = type
                break
            }
        }
        activeClassifierType = tempClassifierType

        activeClassifierIndex = byteHelper.getUInt8()

        hasCustomClassifier = byteHelper.getUInt8() == 1

        streamIndicating = byteHelper.getUInt8()

        val skuValue = byteHelper.getUInt8()
        var tempSku = Sku.UNKNOWN
        for (type in Sku.values()) {
            if (type.value.toInt() == skuValue) {
                tempSku = type
                break
            }
        }
        sku = tempSku

        reservedData = ByteArray(byteHelper.getRemaining())
        var rIndex = 0
        while (byteHelper.hasRemaining()) {
            reservedData[rIndex] = byteHelper.getByte()
            rIndex++
        }
    }
}
