package ru.mirea.shmyglev.a.d.mireaproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;

import ru.mirea.shmyglev.a.d.mireaproject.databinding.FragmentGalleryBinding;
import ru.mirea.shmyglev.a.d.mireaproject.databinding.FragmentMediaRecorderBinding;

public class MediaRecorder extends Fragment {
    private ImageButton recordButton;
    private ImageButton recordButtonPause;
    private ImageButton playButton;
    private ImageButton stopButton;
    private FragmentMediaRecorderBinding binding;

    private static final int REQUEST_CODE_PERMISSION = 200;
    private android.media.MediaRecorder recorder = null;
    private String recordFilePath = null;
    private MediaPlayer player = null;
    private String fileName = "/audiorecordtest.3gp";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMediaRecorderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recordButton = binding.imageButtonRecord;
        recordButtonPause = binding.imageButtonRecordPause;
        playButton = binding.imageButtonPlay1;
        stopButton = binding.imageButtonStop1;



        int audioRecordPermissionStatus = ContextCompat.checkSelfPermission(inflater.getContext(), android.Manifest.permission.RECORD_AUDIO);
        int writeStoragePermissionStatus = ContextCompat.checkSelfPermission(inflater.getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(audioRecordPermissionStatus == PackageManager.PERMISSION_GRANTED &&
                writeStoragePermissionStatus == PackageManager.PERMISSION_GRANTED){
        }else{
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {android.Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recordFilePath =(new File(inflater.getContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                        fileName)).getAbsolutePath();
                binding.textViewStatus.setText("Идет запись...");
                recordButton.setVisibility(View.INVISIBLE);
                recordButton.setClickable(false);
                recordButtonPause.setVisibility(View.VISIBLE);
                recordButtonPause.setClickable(true);
                playButton.setEnabled(false);
                startRecording();
            }
        });

        recordButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textViewStatus.setText("Файл готов к воспроизведению");
                recordButton.setVisibility(View.VISIBLE);
                recordButton.setClickable(true);
                recordButtonPause.setVisibility(View.INVISIBLE);
                recordButtonPause.setClickable(false);
                playButton.setVisibility(View.VISIBLE);
                playButton.setClickable(true);
                playButton.setEnabled(true);
                stopRecording();
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textViewStatus.setText(String.format("Идет воспроизведение файла %s...", fileName) );
                playButton.setVisibility(View.INVISIBLE);
                playButton.setClickable(false);
                stopButton.setVisibility(View.VISIBLE);
                stopButton.setClickable(true);
                recordButton.setEnabled(false);
                startPlaying();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textViewStatus.setText("Ожидается действие");
                playButton.setVisibility(View.VISIBLE);
                playButton.setClickable(true);
                stopButton.setVisibility(View.INVISIBLE);
                stopButton.setClickable(false);
                recordButton.setEnabled(true);
                startPlaying();
            }
        });
        return root;
    }
    private  void startRecording(){
        recorder = new android.media.MediaRecorder();
        recorder.setAudioSource(android.media.MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(android.media.MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(recordFilePath);
        recorder.setAudioEncoder(android.media.MediaRecorder.AudioEncoder.AMR_NB);
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