package ru.mirea.shmyglev.a.d.data_thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import ru.mirea.shmyglev.a.d.data_thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final Runnable runOUT = new Runnable() {
            @Override
            public void run() {
                binding.textView.append("runOnUiThread\n");
            }
        };
        final Runnable runPD = new Runnable() {
            @Override
            public void run() {
                binding.textView.append("postDelayed\n");
            }
        };
        final Runnable runP = new Runnable() {
            @Override
            public void run() {
                binding.textView.append("post\n");
                binding.textView.append("runOnUIThread позволяет выполнить задачу в главном потоке\n" +
                        "postDelayed позволяет выполнить задачу с задержкой\n" +
                        "post ставит задачу в очередь на выполнение в главном потоке");
            }
        };
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    runOnUiThread(runOUT);
                    TimeUnit.SECONDS.sleep(1);
                    binding.textView.postDelayed(runP, 2000);
                    binding.textView.post(runPD);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t.start();
    }
}