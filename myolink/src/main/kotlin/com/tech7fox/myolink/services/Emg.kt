/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.services

import java.util.UUID

/**
 * Describes accessible EMG services/characteristics/descriptors
 */
object Emg {
    private val SERVICE_UUID = UUID.fromString(String.format(MyoService.MYO_SERVICE_BASE_UUID, 0x0005))

    private val EMGDATA0_CHARACTERISTIC_UUID = UUID.fromString(String.format(MyoService.MYO_SERVICE_BASE_UUID, 0x0105))
    private val EMGDATA1_CHARACTERISTIC_UUID = UUID.fromString(String.format(MyoService.MYO_SERVICE_BASE_UUID, 0x0205))
    private val EMGDATA2_CHARACTERISTIC_UUID = UUID.fromString(String.format(MyoService.MYO_SERVICE_BASE_UUID, 0x0305))
    private val EMGDATA3_CHARACTERISTIC_UUID = UUID.fromString(String.format(MyoService.MYO_SERVICE_BASE_UUID, 0x0405))

    @JvmField
    val SERVICE = MyoService(SERVICE_UUID)

    /**
     * Notify-only
     */
    @JvmField
    val EMGDATA0 = MyoCharacteristic(SERVICE, EMGDATA0_CHARACTERISTIC_UUID, "Emg0 Data")
    @JvmField
    val EMGDATA0_DESCRIPTOR = MyoDescriptor(EMGDATA0, MyoCharacteristic.CLIENT_CHARACTERISTIC_CONFIG)
    
    /**
     * Notify-only
     */
    @JvmField
    val EMGDATA1 = MyoCharacteristic(SERVICE, EMGDATA1_CHARACTERISTIC_UUID, "Emg1 Data")
    @JvmField
    val EMGDATA1_DESCRIPTOR = MyoDescriptor(EMGDATA1, MyoCharacteristic.CLIENT_CHARACTERISTIC_CONFIG)
    
    /**
     * Notify-only
     */
    @JvmField
    val EMGDATA2 = MyoCharacteristic(SERVICE, EMGDATA2_CHARACTERISTIC_UUID, "Emg2 Data")
    @JvmField
    val EMGDATA2_DESCRIPTOR = MyoDescriptor(EMGDATA2, MyoCharacteristic.CLIENT_CHARACTERISTIC_CONFIG)
    
    /**
     * Notify-only
     */
    @JvmField
    val EMGDATA3 = MyoCharacteristic(SERVICE, EMGDATA3_CHARACTERISTIC_UUID, "Emg3 Data")
    @JvmField
    val EMGDATA3_DESCRIPTOR = MyoDescriptor(EMGDATA3, MyoCharacteristic.CLIENT_CHARACTERISTIC_CONFIG)

    @JvmStatic
    fun getServiceUUID(): UUID = SERVICE.serviceUUID
}
