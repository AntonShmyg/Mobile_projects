package ru.mirea.shmyglev.a.d.lesson4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mirea.shmyglev.a.d.lesson4.databinding.FragmentButtonsBinding;
import ru.mirea.shmyglev.a.d.lesson4.databinding.FragmentSereachBinding;

public class SereachFragment extends Fragment {
    private FragmentSereachBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSereachBinding.inflate(inflater,container,false);
        binding.editTextMirea3.setText("Мой номер по списку 28");
        View root = binding.getRoot();
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}