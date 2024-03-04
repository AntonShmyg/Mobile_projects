package ru.mirea.shmyglev.a.d.lesson6;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import ru.mirea.shmyglev.a.d.lesson6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPref = getSharedPreferences("mirea_settings", Context.MODE_PRIVATE);
        binding.editTextGroup.setText(sharedPref.getString("GROUP", "Unknown"));
        binding.editTextNumber.setText(String.valueOf(sharedPref.getInt("NUMBER", 0)));
        binding.editTextFilm.setText(sharedPref.getString("FILM", "Unknown"));

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("GROUP", binding.editTextGroup.getText().toString());
                editor.putInt("NUMBER", Integer.parseInt(binding.editTextNumber.getText().toString()));
                editor.putString("FILM", binding.editTextFilm.getText().toString());
                editor.apply();
            }
        });
    }
}