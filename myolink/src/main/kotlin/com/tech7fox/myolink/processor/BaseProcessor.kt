/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.processor

import java.util.ArrayList
import java.util.UUID
import java.util.concurrent.LinkedBlockingQueue

/**
 * A convenience class for creating [Processor] compliant classes.
 * It takes packets submitted via [submit] and processes them sequentially on a worker thread.
 * To create a custom Processor you can extend this and just do your processing in [doProcess].
 */
abstract class BaseProcessor : Processor {
    private val mSubscriptions = ArrayList<UUID>()
    private val mDataListeners = ArrayList<DataListener>()
    private val mQueue = LinkedBlockingQueue<BaseDataPacket>()
    private var mProcessor: Thread? = null
    private var mPacketCounter: Long = 0
    private var mPacketCounterTimeStamp: Long = 0
    private var mPacketThroughput: Long = 0

    override val subscriptions: List<UUID>
        get() = mSubscriptions

    override fun submit(packet: BaseDataPacket) {
        mQueue.add(packet)
        mPacketCounter++
        if (System.currentTimeMillis() - mPacketCounterTimeStamp > 1000) {
            mPacketThroughput = mPacketCounter
            mPacketCounter = 0
            mPacketCounterTimeStamp = System.currentTimeMillis()
        }
    }

    fun getPacketCounter(): Long = mPacketThroughput

    override fun onAdded() {
        mProcessor = Thread(mLoop)
        mRunning = true
        mProcessor?.start()
    }

    override fun onRemoved() {
        mRunning = false
        mProcessor?.interrupt()
    }

    @Volatile
    private var mRunning = false
    
    private val mLoop = Runnable {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_DEFAULT)
        while (mRunning) {
            val packet: BaseDataPacket
            try {
                packet = mQueue.take()
            } catch (e: InterruptedException) {
                continue
            }
            doProcess(packet)
        }
    }

    protected abstract fun doProcess(packet: BaseDataPacket)

    fun getDataListeners(): List<DataListener> = mDataListeners

    protected fun addDataListener(listener: DataListener) {
        if (!mDataListeners.contains(listener))
            mDataListeners.add(listener)
    }

    fun hasListeners(): Boolean = mDataListeners.size > 0

    fun removeDataListener(listener: DataListener) {
        mDataListeners.remove(listener)
    }

    interface DataListener
}
