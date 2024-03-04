package ru.mirea.shmyglev.a.d.audiorecord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

import ru.mirea.shmyglev.a.d.audiorecord.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 200;
    private ActivityMainBinding binding;
    private Button recordButton;
    private Button playButton;
    private MediaRecorder recorder = null;
    private String recordFilePath = null;
    private MediaPlayer player = null;
    private boolean isWork;
    boolean isStartRecording = true;
    boolean isStartPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recordButton = binding.buttonIn;
        playButton = binding.buttonOut;
        playButton.setEnabled(false);
        recordFilePath =(new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                "/audiorecordtest.3gp")).getAbsolutePath();

        int audioRecordPermissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int writeStoragePermissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(audioRecordPermissionStatus == PackageManager.PERMISSION_GRANTED &&
                writeStoragePermissionStatus == PackageManager.PERMISSION_GRANTED){
            isWork = true;
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStartRecording){
                    recordButton.setText("Stop recording");
                    playButton.setEnabled(false);
                    startRecording();
                }else {
                    recordButton.setText("Start recording");
                    playButton.setEnabled(true);
                    stopRecording();
                }
                isStartRecording =!isStartRecording;
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStartPlaying){
                    playButton.setText("Stop playing");
                    recordButton.setEnabled(false);
                    startPlaying();
                }else {
                    playButton.setText("Start playing");
                    recordButton.setEnabled(false);
                    stopPlaying();
                }
                isStartPlaying =!isStartPlaying;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE_PERMISSION:
                isWork = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if(!isWork) finish();
    }
    private  void startRecording(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(recordFilePath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try{
            recorder.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        recorder.start();
    }

    private void stopRecording(){
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    private void startPlaying(){
        player = new MediaPlayer();
        try{
            player.setDataSource(recordFilePath);
            player.prepare();
            player.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void stopPlaying(){
        player.release();
        player = null;
    }
}