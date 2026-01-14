/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.services

import java.util.UUID

/**
 * Describes a certain Myo service via it's UUID
 */
open class MyoService(val serviceUUID: UUID) {
    companion object {
        const val MYO_SERVICE_BASE_UUID = "d506%04X-a904-deb9-4748-2c7f4a124842"
    }
}
