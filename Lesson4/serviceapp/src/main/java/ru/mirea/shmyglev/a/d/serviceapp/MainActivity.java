package ru.mirea.shmyglev.a.d.serviceapp;

import static android.Manifest.permission.FOREGROUND_SERVICE;
import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import ru.mirea.shmyglev.a.d.serviceapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity  implements Runnable{
    ActivityMainBinding binding;
    private int PermissionCode = 200;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Log.d(MainActivity.class.getSimpleName().toString(), "Разрешения получены");
        } else {
            Log.d(MainActivity.class.getSimpleName().toString(), "Нет разрешений");
            ActivityCompat.requestPermissions(this, new String[]{POST_NOTIFICATIONS, FOREGROUND_SERVICE}, PermissionCode);
        }
    }

    public void onClickStart(View v) {
        Intent serviceIntent = new Intent(MainActivity.this, PlayerService.class);
        ContextCompat.startForegroundService(MainActivity.this, serviceIntent);
        binding.ibtnStart.setVisibility(View.INVISIBLE);
        binding.ibtnStart.setClickable(false);
        binding.ibtnStop.setVisibility(View.VISIBLE);
        binding.ibtnStop.setClickable(true);
    }

    public void onClickStop(View v) {
         stopService(new Intent(MainActivity.this, PlayerService.class));
        binding.ibtnStop.setVisibility(View.INVISIBLE);
        binding.ibtnStop.setClickable(false);
        binding.ibtnStart.setVisibility(View.VISIBLE);
        binding.ibtnStart.setClickable(true);
    }

    @Override
    public void run() {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearMediaPlayer();
    }
    private void clearMediaPlayer() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}