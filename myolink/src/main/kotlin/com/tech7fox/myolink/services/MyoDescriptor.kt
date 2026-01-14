/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.services

import java.util.UUID

/**
 * Holds the UUID for a specific descriptor
 */
class MyoDescriptor(
    val characteristic: MyoCharacteristic,
    val descriptorUUID: UUID
) : MyoCharacteristic(
    characteristic.service,
    characteristic.characteristicUUID,
    characteristic.name
)
