/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.processor.imu;

import com.tech7fox.myolink.processor.BaseDataPacket;
import com.tech7fox.myolink.processor.BaseProcessor;
import com.tech7fox.myolink.services.Imu;

/**
 * Processor that converts {@link BaseDataPacket} object into {@link ImuData} objects.
 */
public class ImuProcessor extends BaseProcessor {
    private static final String TAG = "myolink:ImuProcessor";

    public ImuProcessor() {
        super();
        getSubscriptions().add(Imu.IMUDATA.getCharacteristicUUID());
    }

    @Override
    protected void doProcess(BaseDataPacket packet) {
        ImuData imuData = new ImuData(packet);

        for (DataListener listener : getDataListeners()) {
            ImuDataListener imuListener = (ImuDataListener) listener;
            imuListener.onNewImuData(imuData);
        }
    }


    public interface ImuDataListener extends DataListener {
        void onNewImuData(ImuData imuData);
    }

    public void addListener(ImuDataListener listener) {
        super.addDataListener(listener);
    }
}
