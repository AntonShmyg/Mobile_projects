package ru.mirea.shmyglev.a.d.multiactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private TextView sendedText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sendedText= findViewById(R.id.sendedText);

        String text = (String) getIntent().getStringExtra("key");
        sendedText.setText(text);
    }

}