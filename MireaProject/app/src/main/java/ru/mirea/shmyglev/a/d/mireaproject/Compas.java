package ru.mirea.shmyglev.a.d.mireaproject;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import ru.mirea.shmyglev.a.d.mireaproject.databinding.FragmentCompasBinding;

public class Compas extends Fragment implements SensorEventListener {
    private FragmentCompasBinding binding;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Float start = 0f;
    private static final int REQUEST_CODE_PERMISSION = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sensorManager = (SensorManager)inflater.getContext().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        int storagePermissionStatus = ContextCompat.checkSelfPermission(inflater.getContext(), android.Manifest.permission.LOCATION_HARDWARE);
        if (storagePermissionStatus == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[] {android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }
        return root;
    }
    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float valueAzimuth = -event.values[0] * 100;
            float valuePitch = event.values[1];
            float valueRoll = event.values[2];
            RotateAnimation rotate = new RotateAnimation(
                    start, valueAzimuth, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(210);
            binding.imageViewCompass.startAnimation(rotate);
            start = valueAzimuth;
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}