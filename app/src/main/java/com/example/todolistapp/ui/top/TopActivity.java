package com.example.todolistapp.ui.top;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.splashscreen.SplashScreen;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.todolistapp.R;
import com.example.todolistapp.data.entities.Todo;
import com.example.todolistapp.data.entities.TodoSheet;
import com.example.todolistapp.ui.calendar.NewCalenderActivity;
import com.example.todolistapp.ui.garbage.GarbageActivity;
import com.example.todolistapp.ui.newCreate.NewCreateActivity;
import com.example.todolistapp.ui.timeLimit.TimeLimitActivity;
import com.example.todolistapp.ui.top.adapter.TodoSheetPagerAdapter;
import com.example.todolistapp.util.KeyboardUtil;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;
import java.util.List;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TopActivity extends AppCompatActivity implements TextWatcher, ToDoSheetFragment.ToDoSheetFragmentListener {

    private static final String TAG = TopActivity.class.getSimpleName();
    private ViewPager2 viewPager;
    private TopViewModel topViewModel;
    private DrawerLayout drawer;
    private Button bottomSheetCloseBtn;
    private ActivityResultLauncher startForResult =
            registerForActivityResult(new StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    String title = data.getStringExtra(NewCreateActivity.KEY_TITLE);
                    Log.d("TEST", "title = " +title);
                    //　titleをデータベースに入れる
                    topViewModel.insertToDoSheet(title, () -> {
                        // nothing to do
                        });
                    }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // スプラシュスクリーンの設定
        setTheme(R.style.Theme_ToDoListApp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ナビゲーション設定
        initNavigation();
        initNavigationMenu();
        // ボトムシートの設定
        bottomSheetAndFabSettings();
        // ビューモデルの生成
        topViewModel = new ViewModelProvider(this).get(TopViewModel.class);
        topViewModel.init();
        // ビューページャーを取得
        viewPager = findViewById(R.id.view_pager);
        // アダプタを作成
        FragmentStateAdapter pagerAdapter = new TodoSheetPagerAdapter(this, new ArrayList<>());
        // ビューページャーにアダプタをセットする
        viewPager.setAdapter(pagerAdapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d("TEST", "position = " + position);
                //
                topViewModel.setCurrentPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        // タブレイアウトを取得する
        TabLayout tablayout = findViewById(R.id.tab_layout);
        // タブレイアウトとビューページャーの紐付けを行う
        new TabLayoutMediator(tablayout, viewPager, (tab, position) -> {
            List<TodoSheet> dataList = ((TodoSheetPagerAdapter) viewPager.getAdapter()).getTodoSheetList();
            String title = dataList.get(position).title;
            //　TabLayoutに表示するタイトルを設定する
            tab.setText(title);
        }).attach();

        // リスト横の+ボタンを押した時の挙動
        findViewById(R.id.plus_btn).setOnClickListener(v -> {
          // NewCreateActivityに表示する
          moveToNewCreateActivity();
        });

        // observeの設定(LiveDataの値が変更された場合の処理を記述)
        topViewModel.getTodoSheetAll().observe(this, todoSheetList -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (TodoSheet todoSheet: todoSheetList) {
                        // Todoの項目データを取得
                        List<Todo> todoList = topViewModel.getTodoListById(todoSheet.id);
                        for (Todo item: todoList) {
                            Log.d("TEST", "item = " + item.todoTitle);
                        }
                    }
                    // 箱を入れ替える
                    runOnUiThread(() -> {
                        ((TodoSheetPagerAdapter) viewPager.getAdapter()).updateTodoSheetList(todoSheetList);
                    });
                }
            }).start();
        });

        // ゴミ箱ボタンを押した時の挙動
        findViewById(R.id.garbage_btn).setOnClickListener(v -> {
            Intent intent = new Intent(this, GarbageActivity.class);
            startActivity(intent);
        });
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

    // リスト追加用の画面遷移処理
    private void moveToNewCreateActivity() {
        // NewCreateActivityに表示する
        Intent intent = new Intent(this, NewCreateActivity.class);
        // Activityを呼び出して終了したら通知を受け取る
        startForResult.launch(intent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
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
                // カレンダー選択時の処理
                Intent intent = new Intent(this, NewCalenderActivity.class);
                startActivity(intent);
                Log.d(TAG, "カレンダーを選択");
            } else if (itemId == R.id.timeLimit) {
                // 期限付き選択時の処理
                Intent intent = new Intent(this, TimeLimitActivity.class);
                startActivity(intent);
                Log.d(TAG, "期限付きを選択");
            } else if (itemId == R.id.garbageBox) {
                // ゴミ箱選択時の処理
                Intent intent = new Intent(this, GarbageActivity.class);
                startActivity(intent);
                Log.d(TAG, "ゴミ箱を選択");
            } else if (itemId == R.id.newCreate) {
                // 新しいリスト選択時の処理
                moveToNewCreateActivity();
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
        FloatingActionButton addBtn = findViewById(R.id.btn);
        FloatingActionButton garbageBtn = findViewById(R.id.garbage_btn);
        View bottomSheet = findViewById(R.id.bottom_sheet);
        EditText inputTitleText = bottomSheet.findViewById(R.id.input_title_text);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState ==BottomSheetBehavior.STATE_COLLAPSED ) {
                    addBtn.setVisibility(View.VISIBLE);
                    garbageBtn.setVisibility(View.VISIBLE);
                    if (inputTitleText == null) return;
                    KeyboardUtil.hideSoftKeyboard(TopActivity.this, inputTitleText);
                } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    if (inputTitleText == null) return;
                    KeyboardUtil.showSoftKeyboard(TopActivity.this, inputTitleText);
                    addBtn.setVisibility(View.GONE);
                    garbageBtn.setVisibility(View.GONE);
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // nothing to do
            }
        });

        // FloatingActionButtonが押された時の挙動
        addBtn.setOnClickListener(view -> {
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
                // 追加処理
                String title = inputTitleText.getText().toString();
                int position = topViewModel.getCurrentPosition();
                List<TodoSheet> todoSheetList = topViewModel.getTodoSheetAll().getValue();
                TodoSheet currentTodoSheet = todoSheetList.get(position);
                topViewModel.insertTodoItem(currentTodoSheet.id, title, () -> {
                    getTodoList(currentTodoSheet.id, todoList ->
                        runOnUiThread(() -> {
                        // 表示更新処理
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag("f" + position);
                        ((ToDoSheetFragment) fragment).updateTodoDataList(todoList);
                    }));
                });
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

    @Override
    public void getTodoList(int listId, ToDoSheetFragment.GetTodoListCallback callback) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                List<Todo> todoList = topViewModel.getTodoListById(listId);
                callback.onComplete(todoList);
            }
        }.start();
    }

    @Override
    public void onChangeTodoCheck(Todo todo) {
        // Todoデータを更新する
        new Thread(() -> topViewModel.updateTodoData(todo)).start();
    }
}