/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.services

import java.util.UUID

/**
 * Describes accessible Classifier services/characteristics/descriptors
 */
object Classifier {
    private val SERVICE_ID = UUID.fromString(String.format(MyoService.MYO_SERVICE_BASE_UUID, 0x0003))

    private val CLASSIFIEREVENT_CHARACTERISTIC = UUID.fromString(String.format(MyoService.MYO_SERVICE_BASE_UUID, 0x0103))

    @JvmField
    val SERVICE = MyoService(SERVICE_ID)

    @JvmStatic
    fun getServiceUUID(): UUID = SERVICE.serviceUUID

    /**
     * Indicate-only
     */
    @JvmField
    val CLASSIFIEREVENT = MyoCharacteristic(SERVICE, CLASSIFIEREVENT_CHARACTERISTIC, "Classifier Event")

    @JvmField
    val CLASSIFIEREVENT_DESCRIPTOR = MyoDescriptor(CLASSIFIEREVENT, MyoDescriptor.CLIENT_CHARACTERISTIC_CONFIG)
}
