/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.msgs

import com.tech7fox.myolink.services.MyoCharacteristic
import java.util.UUID

/**
 * A subclass of [MyoMsg] for reading data.
 */
class ReadMsg : MyoMsg {
    /**
     * The value that has been read.
     * Initialized with NULL and will be send shortly before the callback is triggered.
     * Depending on [gattStatus] this can also be NULL after the callback.
     *
     * @return The value read from the bluetooth device. Can be NULL.
     */
    var value: ByteArray? = null

    constructor(myoCharacteristic: MyoCharacteristic, callback: Callback?) : 
        this(myoCharacteristic.serviceUUID, myoCharacteristic.characteristicUUID, callback)

    constructor(serviceUUID: UUID, characteristicUUID: UUID, callback: Callback?) : 
        super(serviceUUID, characteristicUUID, null, callback)

    override fun toString(): String {
        return "ReadMsg\nValue: ${value?.contentToString()}\n${super.toString()}"
    }
}
