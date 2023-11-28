package com.example.todolistapp.ui.top;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.todolistapp.R;
import com.example.todolistapp.data.entities.Todo;

import java.util.ArrayList;
import java.util.List;

/**
 * TODOシート
 */
public class ToDoSheetFragment extends Fragment {

    interface ToDoSheetFragmentListener {
        void getTodoList(int listId, GetTodoListCallback callback);
        void onChangeTodoCheck(Todo todo);
    }

    interface GetTodoListCallback{
        void onComplete(List<Todo> todoList);
    }
    private ToDoSheetFragmentListener listener;

    private static final String KEY_LIST_ID = "key_list_id";
    private ToDoSheetFragment() {}

    private RecyclerView recyclerView;
    private List<Todo> todoList = new ArrayList<>();

    public static ToDoSheetFragment newInstance(int listId) {
        Bundle args = new Bundle();
        args.putInt(KEY_LIST_ID,listId);
        ToDoSheetFragment fragment = new ToDoSheetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ToDoSheetFragmentListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_to_do_sheet_fragement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int listId = getArguments().getInt(KEY_LIST_ID);
        // RecyclerViewの取得
        recyclerView = view.findViewById(R.id.recycler_view);
        // LayoutManagerの設定
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        // アダプターの設定
        TodoListRecyclerViewAdapter adapter = new TodoListRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        if (listener != null) {
            listener.getTodoList(listId, todoList -> {
                this.todoList = todoList;
                Activity activity = getActivity();
                if (activity != null) {
                    // adapterにデータの変更を知らせる
                    activity.runOnUiThread(adapter::notifyDataSetChanged);
                }
            });
        }
    }

    public void updateTodoDataList(List<Todo> todoList) {
        this.todoList = todoList;
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     * 打ち消し線表示切り替え処理
     * @param isVisible
     * @param textView
     */
    private void switchStrikethrough(Boolean isVisible, TextView textView) {
        TextPaint paint = textView.getPaint();
        if (isVisible) {
            paint.setFlags(textView.getPaintFlags() | TextPaint.STRIKE_THRU_TEXT_FLAG);
            paint.setAntiAlias(true);
        } else {
            paint.setFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }
    class TodoListRecyclerViewAdapter extends RecyclerView.Adapter<TodoListViewHolder> {

        @NonNull
        @Override
        public TodoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.layout_todo_list_item, parent, false);
            return new TodoListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TodoListViewHolder holder, int position) {
            // 表示データの取得
            Todo todoData = todoList.get(position);
            holder.itemTile.setText(todoData.todoTitle);
            holder.todoCheck.setChecked(todoData.isDone);
            switchStrikethrough(todoData.isDone, holder.itemTile);
            holder.todoCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
                // チェックが変更されたら呼ばれる
                if (listener != null) {
                    todoData.isDone = isChecked;
                    listener.onChangeTodoCheck(todoData);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return todoList.size();
        }
    }

    class TodoListViewHolder extends RecyclerView.ViewHolder {
        CheckBox todoCheck;
        TextView itemTile;
        public TodoListViewHolder(@NonNull View itemView) {
            super(itemView);
            todoCheck = itemView.findViewById(R.id.todo_check_box);
            itemTile = itemView.findViewById(R.id.todo_item_title);
        }
    }
}