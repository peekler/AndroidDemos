package hu.ait.architecturedemo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.hardware.SensorEvent;

public class AccceleroSensorViewModel extends ViewModel{

    private AcceleroSensorLiveData acceleroSensorLiveData;

    public LiveData<SensorEvent> getAcceleroLiveData(Context context) {
        if (acceleroSensorLiveData == null) {
            acceleroSensorLiveData = new AcceleroSensorLiveData(context);
        }
        return acceleroSensorLiveData;
    }
}
