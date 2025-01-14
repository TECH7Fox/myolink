package com.tech7fox.myolink.processor.imu;

import java.util.Arrays;

import com.tech7fox.myolink.processor.BaseDataPacket;
import com.tech7fox.myolink.processor.BaseProcessor;
import com.tech7fox.myolink.services.Imu;
import com.tech7fox.myolink.tools.ByteHelper;
import com.tech7fox.myolink.tools.Logy;

/**
 * @see <a href="https://github.com/thalmiclabs/myo-bluetooth/blob/master/myohw.h#L301">Myo Bluetooth Protocol</a>
 */
public class MotionProcessor extends BaseProcessor {
    private static final String TAG = "myolink:MotionProcessor";

    public MotionProcessor() {
        super();
        getSubscriptions().add(Imu.MOTIONEVENT.getCharacteristicUUID());
    }

    @Override
    protected void doProcess(BaseDataPacket packet) {
        Logy.v(TAG, Arrays.toString(packet.getData()));

        ByteHelper byteHelper = new ByteHelper(packet.getData());
        MotionEvent event = null;
        int typeValue = byteHelper.getUInt8();
        if (typeValue == MotionEvent.Type.TAP.getValue()) {
            TapMotionEvent _event = new TapMotionEvent(packet);
            // TODO possible values are unknown
            int tapDirectionValue = byteHelper.getUInt8();
            int tapCountValue = byteHelper.getUInt8();
            _event.setTapCount(tapCountValue);
            event = _event;
        }
        if (event == null) {
            Logy.e(TAG, "Unknown motion event type!");
            return;
        }


        for (DataListener listener : getDataListeners()) {
            MotionEventListener motionEventListener = (MotionEventListener) listener;
            motionEventListener.onMotionEvent(event);
        }
    }

    public void addListener(MotionEventListener listener) {
        super.addDataListener(listener);
    }

    public interface MotionEventListener extends DataListener {
        void onMotionEvent(MotionEvent motionEvent);
    }
}
