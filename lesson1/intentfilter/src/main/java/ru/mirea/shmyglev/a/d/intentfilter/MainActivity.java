package ru.mirea.shmyglev.a.d.intentfilter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnBrowser;
    private Button btnSendInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBrowser= findViewById(R.id.btnBrowser);
        View.OnClickListener oclBtnBrowser = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri address = Uri.parse("https://www.mirea.ru/");
                Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, address);
                startActivity(openLinkIntent);
            }
        };
        btnBrowser.setOnClickListener(oclBtnBrowser);

        btnSendInfo= findViewById(R.id.btnSendInfo);
        View.OnClickListener oclBtnSendInfo= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Mirea");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Шмыглёв Антон Дмитриевич");
                startActivity(Intent.createChooser(shareIntent,"Мои ФИО" ));
            }
        };
        btnSendInfo.setOnClickListener(oclBtnSendInfo);




    }
}