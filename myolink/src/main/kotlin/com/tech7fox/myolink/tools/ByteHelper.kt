/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.tools

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.UUID

/**
 * Helper class to convert byte[] data received via bluetooth.
 */
class ByteHelper(data: ByteArray) {
    private val mByteBuffer: ByteBuffer = ByteBuffer.wrap(data).apply {
        order(ByteOrder.LITTLE_ENDIAN)
    }

    fun getByte(): Byte = mByteBuffer.get()

    fun getUInt8(): Int = mByteBuffer.get().toInt() and 0xFF

    fun getUInt16(): Int = mByteBuffer.short.toInt()

    fun getUInt16Array(length: Int): IntArray {
        val result = IntArray(length)
        for (i in 0 until length) {
            result[i] = mByteBuffer.short.toInt()
        }
        return result
    }

    fun getUUID(): UUID {
        val low = mByteBuffer.long
        val high = mByteBuffer.long
        return UUID(high, low)
    }

    fun hasRemaining(): Boolean = mByteBuffer.hasRemaining()

    fun getRemaining(): Int = mByteBuffer.remaining()
}
