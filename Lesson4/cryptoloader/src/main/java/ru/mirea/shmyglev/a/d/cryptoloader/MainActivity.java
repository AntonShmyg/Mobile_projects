package ru.mirea.shmyglev.a.d.cryptoloader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import ru.mirea.shmyglev.a.d.cryptoloader.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    public final String TAG = this.getClass().getSimpleName();
    private final int LoaderID = 1234;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
    public static SecretKey generateKey(){
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256,sr);
            return new SecretKeySpec((kg.generateKey()).getEncoded(),"AES");
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }
    public static byte[]encryptMsg(String message, SecretKey secret){
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            return cipher.doFinal(message.getBytes());
        }catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                BadPaddingException | IllegalBlockSizeException e){
            throw new RuntimeException(e);
        }
    }
    public void onClick(View view){
        Bundle bundle = new Bundle();
        String text = String.valueOf(binding.editTextText.getText());
        SecretKey key = generateKey();
        byte[] shiper = encryptMsg(text, key);
        bundle.putByteArray(MyLoader.ARG_WORD,shiper);
        bundle.putByteArray("key", key.getEncoded());
        LoaderManager.getInstance(this).initLoader(LoaderID,bundle,this);
        String text2 = MyLoader.decryptMsg(shiper, key);
        binding.textView.setText(text2);
        Toast.makeText(getApplicationContext(), text2, Toast.LENGTH_LONG).show();
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle bundle) {
        if(id==LoaderID){
            Toast.makeText(this,"onCreateLoader: "+id, Toast.LENGTH_SHORT).show();
            return new MyLoader(this, bundle);
        }
        throw new InvalidParameterException("Invalid loader id");
    }
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        if(loader.getId()==LoaderID){
            Log.d(TAG, "onLoadFinished: " + s);
            Toast.makeText(this,"onLoadFinished: "+s, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        Log.d(TAG, "onLoaderReset");
    }


}