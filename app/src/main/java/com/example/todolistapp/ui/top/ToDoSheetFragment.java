package com.example.todolistapp.ui.top;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        // TODO: アダプターの設定
        TodoListRecyclerViewAdapter adapter = new TodoListRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        if (listener != null) {
            listener.getTodoList(listId, todoList -> {
                // TODO: RecyclerViewにデータを表示する
                for (Todo item : todoList) {
                    Log.d("TEST", "TodoListItem = " + item.todoTitle);
                }
            });
        }
    }
    class TodoListRecyclerViewAdapter extends RecyclerView.Adapter<TodoListViewHolder> {

        @NonNull
        @Override
        public TodoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // TODO:
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull TodoListViewHolder holder, int position) {
            // TODO:
        }

        @Override
        public int getItemCount() {
            // TODO:
            return 0;
        }
    }

    class TodoListViewHolder extends RecyclerView.ViewHolder {
        // TODO:
        public TodoListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}