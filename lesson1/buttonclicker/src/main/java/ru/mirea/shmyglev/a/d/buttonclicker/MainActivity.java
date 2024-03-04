package ru.mirea.shmyglev.a.d.buttonclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView textViewStudent;
    private Button btnWhoAmi;
    private Button btnItIsNotMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewStudent = findViewById(R.id.textViewStudent);
        btnWhoAmi = findViewById(R.id.btnWhoAmI);
        btnItIsNotMe = findViewById(R.id.btnItIsNotMe);
        View.OnClickListener oclBtnWhoAmI = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewStudent.setText("Мой номер по списку 30");
            }
        };
        btnWhoAmi.setOnClickListener(oclBtnWhoAmI);
    }
    public void onMyButtonClick(View view)
    {
        Toast.makeText(this, "Нет, это я сделал", Toast.LENGTH_SHORT).show();
    }
}