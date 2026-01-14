/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.services

import java.util.UUID

/**
 * https://developer.bluetooth.org/gatt/services/Pages/ServiceViewer.aspx?u=org.bluetooth.service.device_information.xml
 */
object Device {
    private val SERVICE_ID = UUID.fromString("0000180A-0000-1000-8000-00805f9b34fb")

    @JvmStatic
    fun getServiceUUID(): UUID = SERVICE.serviceUUID

    /**
     * https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.manufacturer_name_string.xml
     */
    private val MANUFACTURER_NAME_CHARACTERISTIC = UUID.fromString("00002A29-0000-1000-8000-00805f9b34fb")

    @JvmField
    val SERVICE = MyoService(SERVICE_ID)

    @JvmField
    val MANUFACTURER_NAME = MyoCharacteristic(SERVICE, MANUFACTURER_NAME_CHARACTERISTIC, "Manufacturer Name")
}
