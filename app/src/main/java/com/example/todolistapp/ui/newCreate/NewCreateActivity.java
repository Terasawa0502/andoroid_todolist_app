package com.example.todolistapp.ui.newCreate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolistapp.R;

public class NewCreateActivity extends AppCompatActivity {

    public static final String KEY_TITLE = "com.example.todolistapp.key_title";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcreate);

        findViewById(R.id.exit_btn).setOnClickListener(v ->{
            Intent intent = new Intent();
            intent.putExtra(KEY_TITLE, "NewSheet");
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }

    // editテキストを取得して、クリックリスナーに定義
    //　editテキストでいレタ内容をTopActivityに返す

}
