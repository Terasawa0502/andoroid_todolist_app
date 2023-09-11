package com.example.todolistapp.ui.top;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.todolistapp.R;
import com.example.todolistapp.data.TodoDatabase;
import com.example.todolistapp.data.dao.TodoSheetDao;
import com.example.todolistapp.data.entities.TodoSheet;
import com.example.todolistapp.ui.top.adapter.TodoSheetPagerAdapter;
import com.example.todolistapp.util.KeyboardUtil;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ナビゲーションの設定
        initNavigation();
        // ボトムシートの設定
        bottomSheetAndFabSettings();

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

    /**
     * ナビデーションドロワーの設定
     */
    private void initNavigation() {
        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // DrawerToggle
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * ボトムシートの設定
     */
    private void bottomSheetAndFabSettings() {
        FloatingActionButton btn = findViewById(R.id.btn);
        View bottomSheet = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                EditText editText = bottomSheet.findViewById(R.id.text);
                if (newState ==BottomSheetBehavior.STATE_COLLAPSED ) {
                    btn.setVisibility(View.VISIBLE);
                    KeyboardUtil.hideSoftKeyboard(TopActivity.this, editText);
                } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    KeyboardUtil.showSoftKeyboard(TopActivity.this, editText);
                    btn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // nothing to do
            }
        });

        // FloatingActionButtonが押された時の挙動
        btn.setOnClickListener(view -> {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        // BottomSheetPageのCloseButtonが押された時の挙動
        Button bottomSheetCloseBtn = findViewById(R.id.bottom_sheet_close_btn);
        bottomSheetCloseBtn.setOnClickListener(v -> {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });
    }
}