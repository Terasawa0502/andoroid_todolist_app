package com.example.todolistapp.ui.top;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.todolistapp.R;
import com.example.todolistapp.data.TodoDatabase;
import com.example.todolistapp.data.dao.TodoSheetDao;
import com.example.todolistapp.data.entities.TodoSheet;
import com.example.todolistapp.ui.top.adapter.TodoSheetPagerAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TopActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TopViewModel topViewModel;
    private FloatingActionButton btn;
    private View bottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomSheet = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);

        Button bottomSheetCloseBtn = findViewById(R.id.bottom_sheet_close_btn);
        bottomSheetCloseBtn.setOnClickListener(v -> {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(view -> {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//            topViewModel.insertTestSample(this);
        });
        // ViewModelの生成
        topViewModel = new ViewModelProvider(this).get(TopViewModel.class);

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