package ru.mirea.shmyglev.a.d.internalfilestorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import ru.mirea.shmyglev.a.d.internalfilestorage.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private String fileName = "history.txt";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                binding.editTextText.post(new Runnable() {
                    public void run() {
                        binding.editTextText.setText(getTextFromFile());
                    }
                });
            }
        }).start();

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOutputStream outputStream;
                try {
                    outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                    outputStream.write((binding.editTextText.getText().toString()).getBytes());
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private String getTextFromFile() {
        FileInputStream fin;
        try {
            fin = openFileInput(fileName);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            Log.d(LOG_TAG, text);
            return text;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}