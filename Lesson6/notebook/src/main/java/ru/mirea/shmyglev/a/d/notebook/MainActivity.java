package ru.mirea.shmyglev.a.d.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import ru.mirea.shmyglev.a.d.notebook.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
    File file=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isExternalStorageWritable()) {
                    try {
                        writeFileExS();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        binding.buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isExternalStorageReadable()) {
                    try {
                        readFileExS();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
    public boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }
    public boolean isExternalStorageReadable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)||Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            return true;
        }
        return false;
    }
    public void writeFileExS() throws IOException {
        file = new File(path, binding.editTextName.getText().toString());
        if (!binding.editTextName.getText().toString().equals("")){
            FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsoluteFile());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(binding.editTextText.getText().toString());
            outputStreamWriter.close();
            Log.w("ExternalStorage", String.format("Write to file %s successful", binding.editTextName.getText().toString()));
        }
    }
    public void readFileExS() throws IOException {
        file = new File(path, binding.editTextName.getText().toString());
        if (!binding.editTextName.getText().toString().equals("")) {
            FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            List<String> lines = new ArrayList<String>();
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line!=null){
                lines.add(line);
                line = reader.readLine();
            }
            binding.editTextText.setText(lines.toString());
            Log.w("ExternalStorage", String.format("Read from file %s successful", binding.editTextName.getText().toString()));
        }
    }
}