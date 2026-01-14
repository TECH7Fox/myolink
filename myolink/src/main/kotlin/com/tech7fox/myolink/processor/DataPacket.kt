package com.tech7fox.myolink.processor

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic

/**
 * Created by darken on 31.07.2015.
 */
abstract class DataPacket {
    val timeStamp: Long
    val deviceAddress: String

    protected constructor(packet: DataPacket) {
        deviceAddress = packet.deviceAddress
        timeStamp = packet.timeStamp
    }

    protected constructor(deviceAddress: String, timeStamp: Long) {
        this.deviceAddress = deviceAddress
        this.timeStamp = timeStamp
    }
}
