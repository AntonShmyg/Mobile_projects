package ru.mirea.shmyglev.a.d.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;

import ru.mirea.shmyglev.a.d.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private int counter=0;
    private float days=0;
    private float par=0;
    private float daypar=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        TextView textViewThread = findViewById(R.id.textViewThread);
        Thread mainThread = Thread.currentThread();
        textViewThread.setText("Имя текущего потока: "+mainThread.getName());
        mainThread.setName("Мой номер группы: 03, Номер по списку 28, Мой любимый фильм: Хранилище 13");
        textViewThread.append("\n Новое имя потока: "+ mainThread.getName());
        Log.d(MainActivity.class.getSimpleName(), "Stack: " + Arrays.toString(mainThread.getStackTrace()));
        binding.button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        days = Float.parseFloat(binding.editTextDays.getText().toString());
                        par = Float.parseFloat(binding.editTextPar.getText().toString());
                        daypar=par/days;
                        int numberThread=counter++;
                        Log.d("ThreadProject",String.format(
                                "Запущен поток %d студентом группы %s номер по списку %d",
                                numberThread,"БСБО-03-20",28));
                        long endTime = System.currentTimeMillis()+20*1000;
                        while(System.currentTimeMillis()<endTime){
                            synchronized (this){
                                try {
                                    wait(endTime-System.currentTimeMillis());
                                    Log.d(MainActivity.class.getSimpleName(),"Endtime"+endTime);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        Log.d("ThreadProject", "Выполнен поток "+numberThread);
                    }
                }).start();
                binding.TextView.setText(Float.toString(daypar));
            }
        });
    }
}