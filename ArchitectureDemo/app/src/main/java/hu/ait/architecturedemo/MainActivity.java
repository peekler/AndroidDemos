package hu.ait.architecturedemo;

import android.app.PictureInPictureParams;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.hardware.SensorEvent;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends LifecycleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tvStatus = findViewById(R.id.tvStatus);

        getLifecycle().addObserver(new CustomLifeCycleObserver());

        getLifecycle().addObserver(new AcceleroSensorListener(this));

        // LiveData
        /*LiveData<SensorEvent> acceleroSensorLiveData = new AcceleroSensorLiveData(this);
        acceleroSensorLiveData.observe(this, new Observer<SensorEvent>() {
            @Override
            public void onChanged(@Nullable SensorEvent sensorEvent) {
                tvStatus.setText("X: "+sensorEvent.values[0]+", Y: "+sensorEvent.values[1]+" , Z: "+sensorEvent.values[2]);
            }
        });*/

        // ViewModel
        /*AccceleroSensorViewModel acceleroViewModel = ViewModelProviders.of(this).get(AccceleroSensorViewModel.class);
        acceleroViewModel.getAcceleroLiveData(this).observe(this, new Observer<SensorEvent>() {
            @Override
            public void onChanged(@Nullable SensorEvent sensorEvent) {
                tvStatus.setText("X: "+sensorEvent.values[0]+", Y: "+sensorEvent.values[1]+" , Z: "+sensorEvent.values[2]);
            }
        });*/

        Button btnPIP = findViewById(R.id.btnPIP);
        btnPIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterPiPMode();
            }
        });

        final TextView tvAutoSize = findViewById(R.id.tvAutoSize);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(tvAutoSize, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        final Random rand = new Random(System.currentTimeMillis());
        tvAutoSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvAutoSize.setWidth(200+rand.nextInt(600));
                tvAutoSize.setHeight(200+rand.nextInt(500));
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause called", Toast.LENGTH_SHORT).show();
    }

    private void enterPiPMode() {
        //PictureInPictureParams pipParams = new PictureInPictureParams.Builder().build();
        enterPictureInPictureMode();
    }
}
