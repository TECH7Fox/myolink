package com.tech7fox.myolink.processor.imu

import com.tech7fox.myolink.processor.BaseDataPacket
import com.tech7fox.myolink.processor.BaseProcessor
import com.tech7fox.myolink.services.Imu
import com.tech7fox.myolink.tools.ByteHelper
import com.tech7fox.myolink.tools.Logy

/**
 * @see <a href="https://github.com/thalmiclabs/myo-bluetooth/blob/master/myohw.h#L301">Myo Bluetooth Protocol</a>
 */
class MotionProcessor : BaseProcessor() {

    init {
        subscriptions.add(Imu.MOTIONEVENT.characteristicUUID)
    }

    override fun doProcess(packet: BaseDataPacket) {
        Logy.v(TAG, packet.data.contentToString())

        val byteHelper = ByteHelper(packet.data!!)
        val event: MotionEvent?
        val typeValue = byteHelper.getUInt8()
        event = if (typeValue == MotionEvent.Type.TAP.value.toInt()) {
            val _event = TapMotionEvent(packet)
            // TODO possible values are unknown
            val tapDirectionValue = byteHelper.getUInt8()
            val tapCountValue = byteHelper.getUInt8()
            _event.tapCount = tapCountValue
            _event
        } else {
            Logy.e(TAG, "Unknown motion event type!")
            return
        }

        for (listener in getDataListeners()) {
            val motionEventListener = listener as MotionEventListener
            motionEventListener.onMotionEvent(event)
        }
    }

    fun addListener(listener: MotionEventListener) {
        super.addDataListener(listener)
    }

    interface MotionEventListener : DataListener {
        fun onMotionEvent(motionEvent: MotionEvent)
    }

    companion object {
        private const val TAG = "myolink:MotionProcessor"
    }
}
