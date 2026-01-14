/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.tools

import android.os.Build

/**
 * Helper class for Api checks.
 */
object ApiHelper {
    /**
     * @return if >=21
     */
    fun hasLolliPop(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}
