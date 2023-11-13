package com.example.todolistapp.ui.top.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.todolistapp.data.entities.Todo;
import com.example.todolistapp.data.entities.TodoSheet;
import com.example.todolistapp.ui.top.ToDoSheetFragment;

import java.util.ArrayList;
import java.util.List;

public class TodoSheetPagerAdapter extends FragmentStateAdapter {

    private List<TodoSheet> todoSheetList;
    // コンストラクター
    public TodoSheetPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<TodoSheet> todoSheetList) {
        super(fragmentActivity);
        this.todoSheetList = todoSheetList;
    }

    public List<TodoSheet> getTodoSheetList() {
        return todoSheetList;
    }

    public void updateTodoSheetList(List<TodoSheet> todoSheetList) {
        this.todoSheetList = todoSheetList;
        // データ更新用メソッド
        notifyDataSetChanged();
    }
    // pageを作るときに呼ばれる処理
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        TodoSheet data = todoSheetList.get(position);
        return ToDoSheetFragment.newInstance(data.id);
    }

    @Override
    public int getItemCount() {
        return todoSheetList.size();
    }

}
