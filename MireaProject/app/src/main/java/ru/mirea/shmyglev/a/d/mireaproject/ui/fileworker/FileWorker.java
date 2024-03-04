package ru.mirea.shmyglev.a.d.mireaproject.ui.fileworker;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mirea.shmyglev.a.d.mireaproject.databinding.FragmentFileWorkerBinding;

public class FileWorker extends Fragment {
    private FragmentFileWorkerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFileWorkerBinding.inflate(inflater, container, false);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String line = String.valueOf(binding.editTextText.getText());
                Intent intent = new Intent(inflater.getContext(),EncryptionActivity.class);
                intent.putExtra("line", line);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }
}