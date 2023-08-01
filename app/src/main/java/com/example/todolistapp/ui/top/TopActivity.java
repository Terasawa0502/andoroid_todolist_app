package com.example.todolistapp.ui.top;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.todolistapp.R;
import com.example.todolistapp.ui.top.adapter.TodoSheetPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TopActivity extends AppCompatActivity {

    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ViewPagerを取得
        viewPager = findViewById(R.id.view_pager);

        // Adapterを作成
        FragmentStateAdapter pagerAdapter = new TodoSheetPagerAdapter(this);

        // ViewPagerにAdapterをセットする
        viewPager.setAdapter(pagerAdapter);

        // TabLayoutを取得する
        TabLayout tablayout = findViewById(R.id.tab_layout);
        // TabLayoutとViewPagerの紐付けを行う
        new TabLayoutMediator(tablayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //　TabLayoutに表示するタイトルを設定する
                tab.setText("TAB" + (position + 1));
            }
        }).attach();
    }
}