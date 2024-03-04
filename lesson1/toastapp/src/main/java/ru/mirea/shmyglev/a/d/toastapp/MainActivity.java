package ru.mirea.shmyglev.a.d.toastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnCount;
    private TextView textCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCount = findViewById(R.id.btnCnt);
        textCount = findViewById(R.id.textCount);

        View.OnClickListener oclBtnCnt = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strCount= textCount.getText().toString();
                int k = strCount.length();
                Toast toast = Toast.makeText(MainActivity.this, "Студент №30, Группа БСБО-03-20, Количество символов:"+k, Toast.LENGTH_SHORT);
                toast.show();
            }
        };
        btnCount.setOnClickListener(oclBtnCnt);
    }
    public void Count(String textCount){

    }
}

