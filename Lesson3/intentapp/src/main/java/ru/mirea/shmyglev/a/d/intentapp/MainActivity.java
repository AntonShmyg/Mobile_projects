package ru.mirea.shmyglev.a.d.intentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSend = findViewById(R.id.btnSend);


    }
    public void sendText(View v){

        long dateInMillis = System.currentTimeMillis();
        String format = "yyyy-MM-dd HH:mm:ss";
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateString = sdf.format(new Date(dateInMillis));

        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("send", dateString);
        startActivity(intent);

    }


}