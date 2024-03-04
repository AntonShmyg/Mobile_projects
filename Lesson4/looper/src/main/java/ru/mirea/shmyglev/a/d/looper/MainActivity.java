package ru.mirea.shmyglev.a.d.looper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import ru.mirea.shmyglev.a.d.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Handler mainThreadHandler =new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg){
                Log.d(MainActivity.class.getSimpleName(),"Task execute. Result: "+msg.getData().getString("result"));

            }
        };

        MyLooper myLooper = new MyLooper(mainThreadHandler);
        myLooper.start();

        binding.editTextText.setText("Moй номер по списку 28");
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("KEY", "Anton");
                msg.setData(bundle);
                myLooper.mHandler.sendMessage(msg);
            }
        });
    }
}