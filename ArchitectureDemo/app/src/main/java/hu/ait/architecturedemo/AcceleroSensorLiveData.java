package hu.ait.architecturedemo;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import static android.content.Context.SENSOR_SERVICE;

public class AcceleroSensorLiveData extends LiveData<SensorEvent> implements SensorEventListener {

    private Context context;
    private SensorManager sensorManager;
    private Sensor acceleroSensor;

    public AcceleroSensorLiveData(Context context) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        acceleroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onActive() {
        sensorManager.registerListener(this,
                acceleroSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onInactive() {
        sensorManager.unregisterListener(this);
        Toast.makeText(context, "INACTIVATE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        setValue(sensorEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}
}
