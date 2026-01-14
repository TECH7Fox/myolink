/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.processor

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import com.tech7fox.myolink.services.MyoCharacteristic
import com.tech7fox.myolink.services.MyoService
import java.util.UUID

/**
 * The base class for sensor data received from a Myo.
 * If you create a custom processor by implementing [Processor],
 * this is what you will get as data.
 */
class BaseDataPacket : DataPacket {
    /**
     * The service that this data originated from.
     *
     * @return A UUID corresponding to a [MyoService]
     */
    val serviceUUID: UUID
    
    /**
     * The characteristic that this data originated from.
     *
     * @return A UUID corresponding to a [MyoCharacteristic]
     */
    val characteristicUUID: UUID
    
    /**
     * The raw data that was delivered.
     *
     * @return A byte array that can be NULL.
     */
    val data: ByteArray?

    constructor(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) : 
        this(gatt.device.address, characteristic.service.uuid, characteristic.uuid, characteristic.value)

    constructor(deviceAddress: String, serviceUUID: UUID, characteristicUUID: UUID, data: ByteArray?) : 
        super(deviceAddress, System.currentTimeMillis()) {
        this.serviceUUID = serviceUUID
        this.characteristicUUID = characteristicUUID
        this.data = data
    }
}
