package ru.mirea.shmyglev.a.d.control_lesson1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv=findViewById(R.id.textViewStudent);
        setContentView(R.layout.activity_main);
        TextView myTextView =(TextView) findViewById(R.id.textViewStudent);
        myTextView.setText("Toha");
        Button bCall = findViewById(R.id.buttonCall);
        bCall.setText("Call");
    }
}