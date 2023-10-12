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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.todolistapp.R;
import com.example.todolistapp.ui.top.adapter.TodoSheetPagerAdapter;
import com.example.todolistapp.util.KeyboardUtil;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TopActivity extends AppCompatActivity implements TextWatcher {

    private static final String TAG = TopActivity.class.getSimpleName();
    private ViewPager2 viewPager;
    private TopViewModel topViewModel;
    private DrawerLayout drawer;
    private Button bottomSheetCloseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ナビゲーション設定
        initNavigation();
        initNavigationMenu();
        // ボトムシートの設定
        bottomSheetAndFabSettings();
        // ビューモデルの生成
        topViewModel = new ViewModelProvider(this).get(TopViewModel.class);
        // ビューページャーを取得
        viewPager = findViewById(R.id.view_pager);
        // アダプタを作成
        FragmentStateAdapter pagerAdapter = new TodoSheetPagerAdapter(this);
        // ビューページャーにアダプタをセットする
        viewPager.setAdapter(pagerAdapter);
        // タブレイアウトを取得する
        TabLayout tablayout = findViewById(R.id.tab_layout);
        // タブレイアウトとビューページャーの紐付けを行う
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
        // Toolbarのタイトルを非表示にする
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        };
        // DrawerToggle
        drawer = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * ナビゲーションメニューの設定
     */
    public void initNavigationMenu() {
        // NavigationViewの取得
        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.calenderScreen) {
                // TODO: カレンダー選択時の処理
                Log.d(TAG, "カレンダーを選択");
            } else if (itemId == R.id.timeLimit) {
                // TODO: 期限付き選択時の処理
                Log.d(TAG, "期限付きを選択");
            } else if (itemId == R.id.trashBox) {
                // TODO: ゴミ箱選択時の処理
                Log.d(TAG, "ゴミ箱を選択");
            }
            if (drawer != null) {
                // ドロワーを閉じる
                drawer.close();
            }
                return true;
        });
    };

    /**
     * ボトムシートの設定
     */
    private void bottomSheetAndFabSettings() {
        FloatingActionButton btn = findViewById(R.id.btn);
        View bottomSheet = findViewById(R.id.bottom_sheet);
        EditText inputTitleText = bottomSheet.findViewById(R.id.input_title_text);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState ==BottomSheetBehavior.STATE_COLLAPSED ) {
                    btn.setVisibility(View.VISIBLE);
                    if (inputTitleText == null) return;
                    KeyboardUtil.hideSoftKeyboard(TopActivity.this, inputTitleText);
                } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    if (inputTitleText == null) return;
                    KeyboardUtil.showSoftKeyboard(TopActivity.this, inputTitleText);
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
        bottomSheetCloseBtn = findViewById(R.id.bottom_sheet_close_btn);
        bottomSheetCloseBtn.setOnClickListener(v -> {
            String btnTitle = ((Button) v).getText().toString();
            if (btnTitle.equals(getString(R.string.close))){
                // 閉じる処理
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            } else if (btnTitle.equals(getString(R.string.add))) {
                // TODO: 追加処理
            }
        });

        // inputTitleText
        inputTitleText.addTextChangedListener(this);
    }

    // TextWatcher実装メソッド
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // nothing to do
//        Log.d(TAG, "beforeTextChanged: char = " + s + "/ start = " + start + "/ count = " + count + "/ after =" +after);
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        Log.d(TAG, "onTextChanged: char = " + s.toString() +"/ start = " + start + "/ before = " +before + "/ count = " + count);
        if (bottomSheetCloseBtn == null) return;
        if (s.toString().isEmpty()) {
            // 文字が空の場合
            bottomSheetCloseBtn.setText(getString(R.string.close));
        } else {
            // 文字が空じゃない場合
            bottomSheetCloseBtn.setText(getString(R.string.add));
        }
    }
    @Override
    public void afterTextChanged(Editable s) {
        // nothing to do
//        Log.d(TAG, "afterTextChanged: editable = " +s.toString());
    }
}