package ru.mirea.shmyglev.a.d.lesson4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mirea.shmyglev.a.d.lesson4.databinding.FragmentButtonsBinding;
import ru.mirea.shmyglev.a.d.lesson4.databinding.FragmentImageBinding;

public class ImageFragment extends Fragment {
    private FragmentImageBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentImageBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}