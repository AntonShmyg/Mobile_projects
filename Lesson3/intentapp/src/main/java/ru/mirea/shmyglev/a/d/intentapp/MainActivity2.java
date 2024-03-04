package ru.mirea.shmyglev.a.d.intentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private TextView sendedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sendedText = findViewById(R.id.sendedText);

        String text = (String) getIntent().getStringExtra("send");
        sendedText.setText("«КВАДРАТ ЗНАЧЕНИЯ МОЕГО НОМЕРА ПО СПИСКУ В ГРУППЕ СОСТАВЛЯЕТ ЧИСЛО 900, а текущее время"+text);
    }
}