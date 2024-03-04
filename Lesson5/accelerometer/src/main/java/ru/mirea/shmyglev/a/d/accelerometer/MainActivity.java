package ru.mirea.shmyglev.a.d.accelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView azimuthText;
    private TextView pitchText;
    private TextView rollText;
    private SensorManager sensorManager;
    private Sensor accelerometrSensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometrSensor= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        azimuthText = findViewById(R.id.textViewAzimuth);
        pitchText = findViewById(R.id.textViewPitch);
        rollText = findViewById(R.id.textViewRoll);
    }
    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, accelerometrSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() ==Sensor.TYPE_ACCELEROMETER){
            float valueAzimuth = event.values[0];
            float valuePitch = event.values[1];
            float valueRoll = event.values[2];
            azimuthText.setText("Azimuth " + valueAzimuth);
            pitchText.setText("Pitch " + valuePitch);
            rollText.setText("Roll " + valueRoll);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}