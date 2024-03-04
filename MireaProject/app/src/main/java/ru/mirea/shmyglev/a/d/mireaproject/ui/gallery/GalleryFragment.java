package ru.mirea.shmyglev.a.d.mireaproject.ui.gallery;

import static android.Manifest.permission.FOREGROUND_SERVICE;
import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.shmyglev.a.d.mireaproject.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private int PermissionCode = 200;
    private MediaPlayer mediaPlayer;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (ContextCompat.checkSelfPermission(inflater.getContext() , POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Log.d(GalleryFragment.class.getSimpleName().toString(), "Разрешения получены");
        } else {
            Log.d(GalleryFragment.class.getSimpleName().toString(), "Нет разрешений");
            ActivityCompat.requestPermissions(getActivity(), new String[]{POST_NOTIFICATIONS, FOREGROUND_SERVICE}, PermissionCode);
        }
        binding.ibtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent serviceIntent = new Intent(inflater.getContext(), PlayerService.class);
                ContextCompat.startForegroundService(inflater.getContext(), serviceIntent);
                binding.ibtnPlay.setVisibility(View.INVISIBLE);
                binding.ibtnPlay.setClickable(false);
                binding.ibtnStop.setVisibility(View.VISIBLE);
                binding.ibtnStop.setClickable(true);
            }
        });
        binding.ibtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().stopService(
                        new Intent(inflater.getContext(), PlayerService.class));
                binding.ibtnStop.setVisibility(View.INVISIBLE);
                binding.ibtnStop.setClickable(false);
                binding.ibtnPlay.setVisibility(View.VISIBLE);
                binding.ibtnPlay.setClickable(true);
            }
        });
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}