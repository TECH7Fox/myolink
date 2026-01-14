/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink

import android.graphics.Color

/**
 * A helper class to create the byte[] payload for sending instructions to a Myo
 */
object MyoCmds {

    fun buildDeepSleepCmd(): ByteArray {
        return byteArrayOf(
            0x04,
            0
        )
    }

    enum class UnlockType(private val mByte: Byte) {
        LOCK(0x00.toByte()),
        TIMED(0x01.toByte()),
        HOLD(0x02.toByte());

        fun getByte(): Byte = mByte
    }

    fun buildSetUnlockModeCmd(unlockType: UnlockType): ByteArray {
        return byteArrayOf(
            0x0a,
            1,
            unlockType.getByte()
        )
    }

    enum class VibrateType(private val mByte: Byte) {
        NONE(0x00.toByte()),
        SHORT(0x01.toByte()),
        MEDIUM(0x02.toByte()),
        LONG(0x03.toByte());

        fun getByte(): Byte = mByte
    }

    fun buildVibrateCmd(vibrateType: VibrateType): ByteArray {
        return byteArrayOf(
            0x03, // vibrate command
            1, // payload size
            vibrateType.getByte() // vibration type
        )
    }

    fun buildLEDsCmd(logo: Color, bar: Color): ByteArray {
        return byteArrayOf(
            0x06, // set LEDs command
            6, // payload size
            logo.red().toByte(),
            logo.green().toByte(),
            logo.blue().toByte(),
            bar.red().toByte(),
            bar.green().toByte(),
            bar.blue().toByte()
        )
    }

    enum class SleepMode(private val mByte: Byte) {
        /**
         * Go to sleep/standby after a few seconds of inactivity.
         */
        NORMAL(0),
        /**
         * Never go into sleep/standby while the device is connected.
         */
        NEVER(1);

        fun getByte(): Byte = mByte
    }

    fun buildSleepModeCmd(sleepMode: SleepMode): ByteArray {
        return byteArrayOf(
            0x09,
            1,
            sleepMode.getByte()
        )
    }

    enum class EmgMode(private val mByte: Byte) {
        /**
         * No emg data is delivered.
         */
        NONE(0x00.toByte()),
        /**
         * EMG data with powerline interface being filtered out.
         */
        FILTERED(0x02.toByte()),
        /**
         * Raw unfiltered EMG data, this mode will implicitly set [ClassifierMode.DISABLED]
         */
        RAW(0x03.toByte());

        fun getByte(): Byte = mByte
    }

    enum class ImuMode(private val mByte: Byte) {
        /**
         * Do not send IMU data or events.
         */
        NONE(0x00.toByte()),
        /**
         * Send IMU data streams (accelerometer, gyroscope, and orientation).
         */
        DATA(0x01.toByte()),
        /**
         * Send motion events detected by the IMU (e.g. taps).
         */
        EVENTS(0x02.toByte()),
        /**
         * Send both IMU data streams and motion events.
         */
        ALL(0x03.toByte()),
        /**
         * Send raw IMU data streams.
         */
        RAW(0x04.toByte());

        fun getByte(): Byte = mByte
    }

    enum class ClassifierMode(private val mByte: Byte) {
        /**
         * Disable and reset the internal state of the onboard classifier.
         */
        DISABLED(0x00.toByte()),
        /**
         * Send classifier events (poses and arm events).
         */
        ENABLED(0x01.toByte());

        fun getByte(): Byte = mByte
    }

    fun buildSensorModeCmd(emgMode: EmgMode, imuMode: ImuMode, classifierMode: ClassifierMode): ByteArray {
        return byteArrayOf(
            0x01, // mode command
            3, // payload size
            emgMode.getByte(),
            imuMode.getByte(),
            classifierMode.getByte()
        )
    }
}
