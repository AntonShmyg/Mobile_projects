package ru.mirea.shmyglev.a.d.favoritebook;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
public class FavoriteBook extends AppCompatActivity {
    private ActivityResultLauncher<Intent> activityResultLauncher;
    static final String KEY ="book_name";
    static final String USER_MESSAGE="MESSAGE";
    private TextView textFavBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_book);
        textFavBook=findViewById(R.id.txtFavBook);

        ActivityResultCallback<ActivityResult> callback = new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent data = result.getData();
                String userBook = data.getStringExtra(USER_MESSAGE);
                textFavBook.setText(userBook);
            }
        };
        activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),callback);
    }
    public void getInfoAboutBook(View view){
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(KEY, "Архимаг");
        activityResultLauncher.launch(intent);
    }
}