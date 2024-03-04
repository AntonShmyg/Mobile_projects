package ru.mirea.shmyglev.a.d.mireaproject.ui.slideshow;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import ru.mirea.shmyglev.a.d.mireaproject.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private static final int REQUEST_CODE_PERMISSION = 100;
    private static final int CAMERA_REQUEST = 0;
    private Uri imageUri;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        int cameraPermissionStatus = ContextCompat.checkSelfPermission(inflater.getContext(), Manifest.permission.CAMERA);
        int storagePermissionStatus = ContextCompat.checkSelfPermission(inflater.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!(cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus== PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE_PERMISSION);
        }

        ActivityResultCallback<ActivityResult> callback = new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode()== Activity.RESULT_OK){
                    Intent data = result.getData();
                    if (binding.imageView10.getDrawable()==null){
                        binding.imageView10.setImageURI(imageUri);
                    }
                    else if (binding.imageView11.getDrawable()==null){
                        binding.imageView11.setImageURI(imageUri);
                    }
                    else if (binding.imageView12.getDrawable()==null){
                        binding.imageView12.setImageURI(imageUri);
                    }
                    else if (binding.imageView13.getDrawable()==null){
                        binding.imageView13.setImageURI(imageUri);
                    }
                    else if (binding.imageView14.getDrawable()==null){
                        binding.imageView14.setImageURI(imageUri);
                    }
                }
            }
        };
        ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),callback);

        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try{
                    File photoFile = createImageFile();
                    String authorities = inflater.getContext().getPackageName() + ".fileprovider";
                    imageUri = FileProvider.getUriForFile(inflater.getContext(), authorities, photoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    cameraActivityResultLauncher.launch(cameraIntent);
                } catch (IOException e) {
                    e.printStackTrace();}
            }
        });
        return root;
    }

    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "IMAGE_"+timeStamp+"_";
        File storageDirectory = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDirectory);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}