/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.services

import java.util.UUID

/**
 * Describes accessible IMU services/characteristics/descriptors
 */
object Imu {
    private val SERVICE_ID = UUID.fromString(String.format(MyoService.MYO_SERVICE_BASE_UUID, 0x0002))

    private val IMUDATA_CHARACTERISTIC = UUID.fromString(String.format(MyoService.MYO_SERVICE_BASE_UUID, 0x0402))
    private val MOTIONEVENT_CHARACTERISTIC = UUID.fromString(String.format(MyoService.MYO_SERVICE_BASE_UUID, 0x0502))

    @JvmField
    val SERVICE = MyoService(SERVICE_ID)

    @JvmStatic
    fun getServiceUUID(): UUID = SERVICE.serviceUUID

    /**
     * Notify-only
     */
    @JvmField
    val IMUDATA = MyoCharacteristic(SERVICE, IMUDATA_CHARACTERISTIC, "Imu Data")
    @JvmField
    val IMUDATA_DESCRIPTOR = MyoDescriptor(IMUDATA, MyoCharacteristic.CLIENT_CHARACTERISTIC_CONFIG)
    
    /**
     * Indicate-only
     */
    @JvmField
    val MOTIONEVENT = MyoCharacteristic(SERVICE, MOTIONEVENT_CHARACTERISTIC, "Motion Event")
    @JvmField
    val MOTIONEVENT_DESCRIPTOR = MyoDescriptor(MOTIONEVENT, MyoCharacteristic.CLIENT_CHARACTERISTIC_CONFIG)
}
