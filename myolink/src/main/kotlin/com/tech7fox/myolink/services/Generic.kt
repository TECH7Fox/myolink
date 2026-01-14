/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.services

import java.util.UUID

/**
 * https://developer.bluetooth.org/gatt/services/Pages/ServiceViewer.aspx?u=org.bluetooth.service.generic_access.xml
 */
object Generic {
    private val SERVICE_ID = UUID.fromString("00001800-0000-1000-8000-00805f9b34fb")

    @JvmStatic
    fun getServiceUUID(): UUID = SERVICE.serviceUUID

    @JvmField
    val SERVICE = MyoService(SERVICE_ID)

    /**
     * https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.gap.device_name.xml
     */
    private val DEVICE_NAME_CHARACTERISTIC = UUID.fromString("00002A00-0000-1000-8000-00805f9b34fb")
    
    /**
     * https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.gap.appearance.xml
     */
    private val APPEARANCE_CHARACTERISTIC = UUID.fromString("00002A01-0000-1000-8000-00805f9b34fb")
    
    /**
     * https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.gap.peripheral_preferred_connection_parameters.xml
     */
    private val PERIPHERAL_PREFERRED_CONNECTION_PARAMETERS_CHARACTERISTIC = UUID.fromString("00002A04-0000-1000-8000-00805f9b34fb")

    /**
     * Myo device name.
     * Supports read/write.
     */
    @JvmField
    val DEVICE_NAME = MyoCharacteristic(SERVICE, DEVICE_NAME_CHARACTERISTIC, "Device Name")
    
    @JvmField
    val APPEARANCE = MyoCharacteristic(SERVICE, APPEARANCE_CHARACTERISTIC, "Appearance")
    
    @JvmField
    val PERIPHERAL_PREFERRED_CONNECTION_PARAMETERS = MyoCharacteristic(SERVICE, PERIPHERAL_PREFERRED_CONNECTION_PARAMETERS_CHARACTERISTIC, "Peripheral Preferred Connection Parameters")
}
