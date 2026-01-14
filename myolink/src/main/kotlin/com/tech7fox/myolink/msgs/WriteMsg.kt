/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.msgs

import com.tech7fox.myolink.services.MyoCharacteristic
import com.tech7fox.myolink.services.MyoDescriptor
import java.util.UUID

/**
 * A subclass of [MyoMsg] for writing data.
 */
class WriteMsg : MyoMsg {
    val data: ByteArray

    constructor(characteristic: MyoCharacteristic, data: ByteArray, callback: Callback?) : 
        this(characteristic.serviceUUID, characteristic.characteristicUUID, null, data, callback)

    constructor(descriptor: MyoDescriptor, data: ByteArray, callback: Callback?) : 
        this(descriptor.serviceUUID, descriptor.characteristicUUID, descriptor.descriptorUUID, data, callback)

    constructor(serviceUUID: UUID, characteristicUUID: UUID, descriptorUUID: UUID?, data: ByteArray, callback: Callback?) : 
        super(serviceUUID, characteristicUUID, descriptorUUID, callback) {
        this.data = data
    }

    override fun toString(): String {
        return "WriteMsg\nData: ${data.contentToString()}\n${super.toString()}"
    }
}
