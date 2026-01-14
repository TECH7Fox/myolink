/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.processor

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import com.tech7fox.myolink.services.MyoCharacteristic
import java.util.UUID

/**
 * Interface for processors to be used with [com.tech7fox.myolink.BaseMyo.addProcessor].
 * Also see [BaseProcessor].
 */
interface Processor {
    /**
     * If new data arrives [com.tech7fox.myolink.BaseMyo],
     * will [submit], if this list contains the UUID of the characteristic delivered by
     * [android.bluetooth.BluetoothGattCallback.onCharacteristicChanged].
     *
     * Don't modify this list live or [com.tech7fox.myolink.BaseMyo] might throw a [java.util.ConcurrentModificationException].
     *
     * @return A list of unique characteristic UUIDs from [MyoCharacteristic.characteristicUUID]
     */
    val subscriptions: List<UUID>

    /**
     * Will be called when new data arrived that this processor is subscribed for.
     * Don't execute expensive routines inside this method!
     * Spending too much time in this method blocks the
     * [android.bluetooth.BluetoothGattCallback.onCharacteristicChanged]
     * of [com.tech7fox.myolink.BaseMyo] and can lead to packet loss!
     * It is strongly recommended to just add the packet to a data structure and process it on a
     * different thread.
     *
     * @param packet the packet that [com.tech7fox.myolink.BaseMyo] created.
     */
    fun submit(packet: BaseDataPacket)

    /**
     * Called when the processor is added to a Myo. Use it to e.g., start your workers.
     */
    fun onAdded()

    /**
     * Called when the processor is removed from a Myo. Use it to e.g., stop your workers.
     */
    fun onRemoved()
}
