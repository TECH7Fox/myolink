/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink

import java.util.ArrayList

/**
 * Based on http://stackoverflow.com/a/24043510/1251958
 * Convenience wrapper for one data entry of an BLE advertising message
 */
data class AdRecord(
    val length: Int,
    val type: Int,
    val data: ByteArray
) {
    override fun toString(): String {
        return "AdRecord: length:$length, type:$type, data:${data.contentToString()}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AdRecord

        if (length != other.length) return false
        if (type != other.type) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = length
        result = 31 * result + type
        result = 31 * result + data.contentHashCode()
        return result
    }

    companion object {
        fun parseScanRecord(scanRecord: ByteArray): List<AdRecord> {
            val records = ArrayList<AdRecord>()

            var index = 0
            while (index < scanRecord.size) {
                val length = scanRecord[index++].toInt()
                // Done once we run out of records
                if (length == 0) break

                val type = scanRecord[index].toInt()
                // Done if our record isn't a valid type
                if (type == 0) break

                val data = scanRecord.copyOfRange(index + 1, index + length)

                records.add(AdRecord(length, type, data))
                // Advance
                index += length
            }
            return records
        }
    }
}
