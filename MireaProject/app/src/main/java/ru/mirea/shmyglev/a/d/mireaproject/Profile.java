package ru.mirea.shmyglev.a.d.mireaproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import ru.mirea.shmyglev.a.d.mireaproject.databinding.FragmentProfileBinding;

public class Profile extends Fragment {
    private FragmentProfileBinding binding;
    private SharedPreferences sharedPref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPref = getActivity().getSharedPreferences(binding.etName.getText().toString(), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("NAME", binding.etName.getText().toString());
                editor.putString("NUMBER", binding.etNumber.getText().toString());
                editor.putString("NOTE", binding.etNote.getText().toString());
                editor.apply();
                Toast.makeText( getContext(), String.format("Сохранен профиль %s", binding.etName.getText().toString()), Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnLoadProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPref = getActivity().getSharedPreferences(binding.etName.getText().toString(), Context.MODE_PRIVATE);
                binding.etName.setText(sharedPref.getString("NAME","Unknown"));
                binding.etNumber.setText(sharedPref.getString("NUMBER","0"));
                binding.etNote.setText(sharedPref.getString("NOTE","Unknown"));
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}