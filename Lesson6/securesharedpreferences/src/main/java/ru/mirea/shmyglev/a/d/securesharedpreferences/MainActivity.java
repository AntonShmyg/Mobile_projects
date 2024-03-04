package ru.mirea.shmyglev.a.d.securesharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.view.View;

import java.io.IOException;
import java.security.GeneralSecurityException;

import ru.mirea.shmyglev.a.d.securesharedpreferences.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    SharedPreferences secureSharedPreferences = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
        try {
            String mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);
            secureSharedPreferences = EncryptedSharedPreferences.create(
                    "secret_shared_pref",
                    mainKeyAlias,
                    getBaseContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            secureSharedPreferences.edit().putString("NAME", "Эдди Макклинток").apply();
            secureSharedPreferences.edit().putInt("PHOTO", R.drawable.eddie).apply();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.textView.setText(secureSharedPreferences.getString("NAME","unknown"));
                binding.imageView.setImageResource(secureSharedPreferences.getInt("PHOTO", 0));
            }
        });
    }
}