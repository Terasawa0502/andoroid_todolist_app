package com.example.todolistapp.ui.top.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.todolistapp.ui.top.ToDoSheetFragement;

public class TodoSheetPagerAdapter extends FragmentStateAdapter {

    // コンストラクター
    public TodoSheetPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    // pageを作るときに呼ばれる処理
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ToDoSheetFragement.newInstance();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
