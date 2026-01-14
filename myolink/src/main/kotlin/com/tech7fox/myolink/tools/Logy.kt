/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.tools

import android.util.Log
import com.tech7fox.myolink.BuildConfig

/**
 * Wrapper class for [Log] that allows to fine tune what gets logged.
 */
object Logy {
    const val SILENT = -2
    const val QUIET = -1
    const val NORMAL = 0
    const val DEBUG = 1
    const val VERBOSE = 2

    var sLoglevel = if (BuildConfig.DEBUG) VERBOSE else QUIET

    fun v(c: String, s: String) {
        if (sLoglevel >= VERBOSE) {
            Log.v(c, s)
        }
    }

    fun d(c: String, s: String) {
        if (sLoglevel >= DEBUG) {
            Log.d(c, s)
        }
    }

    fun i(c: String, s: String) {
        if (sLoglevel >= NORMAL) {
            Log.i(c, s)
        }
    }

    fun w(c: String, s: String) {
        if (sLoglevel > QUIET) {
            Log.w(c, s)
        }
    }

    fun w(c: String, s: String, tr: Throwable) {
        if (sLoglevel > QUIET) {
            Log.w(c, s, tr)
        }
    }

    fun e(c: String, s: String) {
        if (sLoglevel != SILENT) {
            Log.e(c, s)
        }
    }

    fun e(c: String, s: String, tr: Throwable) {
        if (sLoglevel != SILENT) {
            Log.e(c, s, tr)
        }
    }
}
