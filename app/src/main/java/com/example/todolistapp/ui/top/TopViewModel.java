package com.example.todolistapp.ui.top;

import androidx.lifecycle.ViewModel;
import com.example.todolistapp.data.entities.TodoSheet;
import com.example.todolistapp.data.repositories.ToDoListRepository;

import java.util.List;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TopViewModel extends ViewModel {

    interface InsertTodoSheetCallback {
        void onComplete();
    }

    interface GetTodoSheetAllCallback {
        void onComplete(List<TodoSheet> todoSheetList);
    }
    private ToDoListRepository repository;

    @Inject
    TopViewModel(ToDoListRepository repository) {
        this.repository = repository;
    }

    public void getTodoSheetAll(GetTodoSheetAllCallback callback) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                List<TodoSheet> todoSheetList =repository.getAllTodoSheet();
                callback.onComplete(todoSheetList);
            }
        }.start();
    }
    public void insertToDoSheet(String title, InsertTodoSheetCallback callback) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                TodoSheet todoSheet = new TodoSheet();
                todoSheet.title = title;
                repository.insertTodoSheet(todoSheet);
                callback.onComplete();
            }
        }.start();
    }
}
