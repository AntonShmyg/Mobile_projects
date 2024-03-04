package ru.mirea.shmyglev.a.d.mireaproject.ui.fileworker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import ru.mirea.shmyglev.a.d.mireaproject.R;
import ru.mirea.shmyglev.a.d.mireaproject.databinding.FragmentEncryptionActivityBinding;

public class EncryptionActivity extends AppCompatActivity {
    private FragmentEncryptionActivityBinding binding;
    private String fileName = "mirea.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentEncryptionActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String line = (String) getIntent().getSerializableExtra("line");
        SecretKey key = generateKey();
        byte[] shiper = encryptMsg(line,key);
        String str = new String(shiper, StandardCharsets.UTF_8);
        binding.editTextFileName.setText(fileName);
        binding.editTextEnMess.setText(str);
        binding.buttonFileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                    writeFileToExternalStorage();
                }
            }
        });
        binding.buttonFileLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                        Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState()))
                    readFileFromExternalStorage();
            }
        });
    }
    public void writeFileToExternalStorage()	{
        File path =	Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file =	new	File(path,	binding.editTextFileName.getText() + ".txt");
        try	{
            FileOutputStream fileOutputStream =	new	FileOutputStream(file.getAbsoluteFile());
            OutputStreamWriter output =	new	OutputStreamWriter(fileOutputStream);
            output.write(String.valueOf(binding.editTextEnMess.getText()));
            output.close();
        }	catch	(IOException e)	{
            Log.w("ExternalStorage",	"Error	writing	"	+	file,	e);
        }
    }
    public void	readFileFromExternalStorage() {
        File path =	Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file =	new	File(path,binding.editTextFileName.getText() + ".txt");
        try	{
            FileInputStream fileInputStream	= new	FileInputStream(file.getAbsoluteFile());
            InputStreamReader inputStreamReader	= new	InputStreamReader(fileInputStream,	StandardCharsets.UTF_8);
//            List<String> lines = new ArrayList<String>();
            BufferedReader reader =	new BufferedReader(inputStreamReader);
            String line	= reader.readLine();
            Log.d("ExternalStorage", String.format("Read from file %s successful",	line));
            binding.editTextEnMess.setText(line);
        }	catch (Exception	e) {
            Log.d("ExternalStorage", String.format("Read from file %s failed",	e.getMessage()));
        }
    }
    public	static SecretKey generateKey(){
        try	{
            SecureRandom sr	= SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any	ata	usedas	random	seed".getBytes());
            KeyGenerator kg	= KeyGenerator.getInstance("AES");
            kg.init(256,	sr);
            return	new SecretKeySpec((kg.generateKey()).getEncoded(),	"AES");
        }	catch	(NoSuchAlgorithmException e)	{
            throw	new	RuntimeException(e);
        }
    }
    public	static	byte[]	encryptMsg(String	message,	SecretKey	secret)	{
        Cipher cipher	=	null;
        try	{
            cipher	=	Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE,	secret);
            return	cipher.doFinal(message.getBytes());
        }	catch	(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                       BadPaddingException | IllegalBlockSizeException e)	{
            throw	new	RuntimeException(e);
        }
    }
}