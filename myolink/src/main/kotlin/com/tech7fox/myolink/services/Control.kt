/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.services

import java.util.UUID

/**
 * Data necessary to control the Myo devices i.e., send commands
 */
object Control {
    private val SERVICE_ID = UUID.fromString(String.format(MyoService.MYO_SERVICE_BASE_UUID, 0x0001))

    private val MYOINFO_CHARACTERISTIC = UUID.fromString(String.format(MyoService.MYO_SERVICE_BASE_UUID, 0x0101))
    private val FIRMWARE_VERSION_CHARACTERISTIC = UUID.fromString(String.format(MyoService.MYO_SERVICE_BASE_UUID, 0x0201))
    private val COMMAND_CHARACTERISTIC = UUID.fromString(String.format(MyoService.MYO_SERVICE_BASE_UUID, 0x0401))

    @JvmField
    val SERVICE = MyoService(SERVICE_ID)
    
    /**
     * Read-only
     */
    @JvmField
    val MYOINFO = MyoCharacteristic(SERVICE, MYOINFO_CHARACTERISTIC, "Myo Info")
    
    /**
     * Read-only
     */
    @JvmField
    val FIRMWARE_VERSION = MyoCharacteristic(SERVICE, FIRMWARE_VERSION_CHARACTERISTIC, "Myo Firmware Version")
    
    /**
     * Write-only
     */
    @JvmField
    val COMMAND = MyoCharacteristic(SERVICE, COMMAND_CHARACTERISTIC, "Myo Command")

    @JvmStatic
    fun getServiceUUID(): UUID = SERVICE.serviceUUID
}
