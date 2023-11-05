package com.example.todolistapp.ui.timeLimit;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolistapp.R;

public class TimeLimitActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timelimit);

        // 戻るボタンを押した時の挙動
        findViewById(R.id.timeLimit_back_btn).setOnClickListener(v -> {
            finish();
        });

        // TODO:期限付きのTodoリスト一覧を表示
    }
}
