/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.services

import java.util.UUID

/**
 * Access to battery information
 */
object Battery {
    private val SERVICE_ID = UUID.fromString("0000180F-0000-1000-8000-00805f9b34fb")

    @JvmField
    val SERVICE = MyoService(SERVICE_ID)

    @JvmStatic
    fun getServiceUUID(): UUID = SERVICE.serviceUUID

    private val BATTERY_LEVEL_CHARACTERISTIC = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb")
    
    /**
     * Current Myo battery level.
     * Supports read/notify
     */
    @JvmField
    val BATTERYLEVEL = MyoCharacteristic(SERVICE, BATTERY_LEVEL_CHARACTERISTIC, "Battery Level")
}
