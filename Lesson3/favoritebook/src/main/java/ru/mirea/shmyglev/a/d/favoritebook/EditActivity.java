package ru.mirea.shmyglev.a.d.favoritebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    private EditText editFavBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Bundle extras= getIntent().getExtras();
        if(extras!=null){
            TextView textFavShareBook = findViewById(R.id.tvShareTextBook);
            String favBook = extras.getString(FavoriteBook.KEY);
            textFavShareBook.setText(String.format("Моя любимая книга: %s", favBook));
        }
    }
    public void sendInfoAboutBook(View view){
        Intent data = new Intent();
        editFavBook = findViewById(R.id.editFavBook);
        String text = editFavBook.getText().toString();
        data.putExtra(FavoriteBook.USER_MESSAGE, text);
        setResult(Activity.RESULT_OK, data);
        finish();
    }
}