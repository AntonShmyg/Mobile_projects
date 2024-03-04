package ru.mirea.shmyglev.a.d.multiactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnSend;
    private TextView textSend;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSend= findViewById(R.id.btnSend);
        textSend= findViewById(R.id.textSend);

        View.OnClickListener oclBtnSend= new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strSend = textSend.getText().toString();
                sendText(strSend);
            }
        };
        btnSend.setOnClickListener(oclBtnSend);
    }

    public void sendText(String text){
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("key", text);
        startActivity(intent);
    }
}