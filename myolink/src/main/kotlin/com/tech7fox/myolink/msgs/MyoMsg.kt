/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.msgs

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import com.tech7fox.myolink.services.MyoCharacteristic
import com.tech7fox.myolink.services.MyoDescriptor
import com.tech7fox.myolink.services.MyoService
import java.util.UUID

/**
 * The base class for all communication done with a Myo.
 * Intended to be used with [com.tech7fox.myolink.BaseMyo.submit].
 *
 * Normally you would want to use one of its subclasses:
 * [WriteMsg], [ReadMsg]
 */
open class MyoMsg @JvmOverloads constructor(
    val serviceUUID: UUID,
    val characteristicUUID: UUID,
    val descriptorUUID: UUID? = null,
    val callback: Callback? = null
) {
    var retryCounter: Int = -1
        private set
    var gattStatus: Int? = null
    var state: State = State.NEW

    /**
     * Values reflecting state of this [MyoMsg]
     */
    enum class State {
        /**
         * Message has not been submitted yet via [com.tech7fox.myolink.BaseMyo.submit].
         */
        NEW,
        /**
         * Message was send and possibly confirmed.
         */
        SUCCESS,
        /**
         * The message failed to be transmitted.
         * This includes failure to submit, timeouts, running out of retries.
         */
        ERROR
    }

    interface Callback {
        fun onResult(msg: MyoMsg)
    }

    fun decreaseRetryCounter(): Int {
        if (retryCounter > 0) retryCounter--
        return retryCounter
    }

    /**
     * How often this message should be retried when:
     * [gattStatus] != [android.bluetooth.BluetoothGatt.GATT_SUCCESS]
     * after a transmission.
     *
     * @param retryCounter -1 for infinite tries otherwise >=0 tries
     */
    fun setRetryCounter(retryCounter: Int) {
        this.retryCounter = retryCounter
    }

    /**
     * An identifier string for this message. This is not unique.
     * Two messages can have the identifier but as we are transmitting messages in sequence and not in parallel, it is not an issue.
     */
    val identifier: String
        get() {
            val builder = StringBuilder()
            builder.append(serviceUUID.toString())
            builder.append(":").append(characteristicUUID.toString())
            if (descriptorUUID != null)
                builder.append(":").append(descriptorUUID.toString())
            return builder.toString()
        }

    override fun toString(): String {
        return "Identifier: $identifier"
    }

    companion object {
        /**
         * A convenience method to generate the identifier based on a [BluetoothGattCharacteristic] object.
         * Also see [identifier].
         */
        @JvmStatic
        fun toIdentifier(characteristic: BluetoothGattCharacteristic): String {
            val builder = StringBuilder()
            builder.append(characteristic.service.uuid.toString())
            builder.append(":").append(characteristic.uuid.toString())
            return builder.toString()
        }

        /**
         * See [toIdentifier]
         */
        @JvmStatic
        fun toIdentifier(descriptor: BluetoothGattDescriptor): String {
            val builder = StringBuilder()
            builder.append(descriptor.characteristic.service.uuid.toString())
            builder.append(":").append(descriptor.characteristic.uuid.toString())
            builder.append(":").append(descriptor.uuid.toString())
            return builder.toString()
        }
    }
}
