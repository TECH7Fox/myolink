package com.tech7fox.myolink.processor.classifier

import com.tech7fox.myolink.processor.BaseDataPacket
import com.tech7fox.myolink.processor.BaseProcessor
import com.tech7fox.myolink.services.Classifier
import com.tech7fox.myolink.tools.ByteHelper
import com.tech7fox.myolink.tools.Logy

/**
 * @see <a href="https://github.com/thalmiclabs/myo-bluetooth/blob/master/myohw.h#L345">Myo Bluetooth Protocol</a>
 */
class ClassifierProcessor : BaseProcessor() {

    init {
        subscriptions.add(Classifier.CLASSIFIEREVENT.characteristicUUID)
    }

    override fun doProcess(packet: BaseDataPacket) {
        Logy.v(TAG, packet.data.contentToString())

        val byteHelper = ByteHelper(packet.data!!)
        val typeValue = byteHelper.getUInt8()
        val event: ClassifierEvent? = when (typeValue.toByte()) {
            ClassifierEvent.Type.ARM_SYNCED.value -> ArmSyncedClassifierEvent(packet)
            ClassifierEvent.Type.ARM_UNSYNCED.value -> ClassifierEvent(packet, ClassifierEvent.Type.ARM_UNSYNCED)
            ClassifierEvent.Type.POSE.value -> PoseClassifierEvent(packet)
            ClassifierEvent.Type.UNLOCKED.value -> ClassifierEvent(packet, ClassifierEvent.Type.UNLOCKED)
            ClassifierEvent.Type.LOCKED.value -> ClassifierEvent(packet, ClassifierEvent.Type.LOCKED)
            ClassifierEvent.Type.SYNC_FAILED.value -> SyncFailedClassifierEvent(packet)
            ClassifierEvent.Type.WARM_UP_RESULT.value -> WarmUpResultClassifierEvent(packet)
            else -> {
                Logy.e(TAG, "Unknown classifier event type!")
                null
            }
        }

        if (event == null) {
            return
        }
        
        for (listener in getDataListeners()) {
            val motionEventListener = listener as ClassifierEventListener
            motionEventListener.onClassifierEvent(event)
        }
    }

    interface ClassifierEventListener : DataListener {
        fun onClassifierEvent(classifierEvent: ClassifierEvent)
    }

    fun addListener(listener: ClassifierEventListener) {
        super.addDataListener(listener)
    }

    companion object {
        private const val TAG = "myolink:ClassifierProcessor"
    }
}
