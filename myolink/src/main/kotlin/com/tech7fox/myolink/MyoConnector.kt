/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import com.tech7fox.myolink.services.Control
import com.tech7fox.myolink.tools.ByteHelper
import com.tech7fox.myolink.tools.Logy
import java.util.ArrayList
import java.util.HashMap

/**
 * Wrapper for [BluetoothAdapter] that finds Myo devices
 */
class MyoConnector(context: Context) : BluetoothAdapter.LeScanCallback {
    val context: Context = context.applicationContext
    private val mBluetoothAdapter: BluetoothAdapter
    private val mDeviceMap = HashMap<String, Myo>()
    private val mScanMap = HashMap<String, Myo>()
    private var mScanRunnable: Runnable? = null

    init {
        val bluetoothManager = this.context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothAdapter = bluetoothManager.adapter
    }

    /**
     * Scans for Myo devices (timed scan with callback at the end)
     * Results will only be returned after the scan finished.
     *
     * @param timeout  How long the scan lasts in milliseconds.
     * @param callback optional callback with results, also available via [getMyos]
     * @return true if a scan was started, false if a scan was already running.
     */
    fun scan(timeout: Long, callback: ScannerCallback?): Boolean {
        if (mScanRunnable != null) return false
        
        mScanMap.clear()
        mScanRunnable = Runnable {
            @SuppressLint("MissingPermission")
            mBluetoothAdapter.startLeScan(this@MyoConnector)
            try {
                Thread.sleep(timeout)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            Logy.d(TAG, "Scan stopped (timeout:$timeout)")
            @SuppressLint("MissingPermission")
            mBluetoothAdapter.stopLeScan(this@MyoConnector)
            mScanRunnable = null
            callback?.onScanFinished(ArrayList(mDeviceMap.values))
        }
        Thread(mScanRunnable).start()
        return true
    }

    override fun onLeScan(device: BluetoothDevice, rssi: Int, scanRecord: ByteArray) {
        val adRecords = AdRecord.parseScanRecord(scanRecord)
        var uuid: java.util.UUID? = null
        for (adRecord in adRecords) {
            // TYPE_UUID128_INC
            if (adRecord.type == 0x6) {
                uuid = ByteHelper(adRecord.data).getUUID()
                break
            }
        }
        if (Control.getServiceUUID() == uuid) {
            if (!mScanMap.containsKey(device.address)) {
                var myo = mDeviceMap[device.address]
                if (myo == null) {
                    myo = Myo(context, device)
                    mDeviceMap[device.address] = myo
                }
                mScanMap[device.address] = myo
            }
        }
    }

    interface ScannerCallback {
        fun onScanFinished(myos: List<Myo>)
    }

    fun getMyos(): ArrayList<Myo> {
        return ArrayList(mDeviceMap.values)
    }

    companion object {
        private const val TAG = "myolink:RawMyoConnector"
    }
}
