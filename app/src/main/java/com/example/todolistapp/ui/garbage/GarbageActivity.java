package com.example.todolistapp.ui.garbage;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolistapp.R;

public class GarbageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garbage);

        // 戻るボタンを押した時の挙動
        findViewById(R.id.garbage_back_btn).setOnClickListener(v -> {
            finish();
        });

        // TODO:削除済みのTodoリスト一覧を表示

        // TODO:全削除ボタンを押した時の挙動
        findViewById(R.id.all_delete_btn).setOnClickListener(view -> {
            finish();
        });

    }
}
