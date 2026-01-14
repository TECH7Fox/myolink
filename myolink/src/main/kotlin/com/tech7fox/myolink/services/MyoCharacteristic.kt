/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.services

import java.util.UUID

/**
 * Holds the UUID for a specific characteristic
 */
open class MyoCharacteristic(
    val service: MyoService,
    val characteristicUUID: UUID,
    val name: String
) {
    val serviceUUID: UUID
        get() = service.serviceUUID

    companion object {
        @JvmField
        val CLIENT_CHARACTERISTIC_CONFIG: UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
    }
}
