package com.example.todolistapp.ui.top;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolistapp.data.entities.Todo;
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

    private ToDoListRepository repository;
    private LiveData<List<TodoSheet>> listLiveData;

    @Inject
    TopViewModel(ToDoListRepository repository) {
        this.repository = repository;
    }
    public void init() {
        listLiveData = repository.getAllTodoSheet();
    }
    public LiveData<List<TodoSheet>> getTodoSheetAll() {
        return listLiveData;
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
    public List<Todo> getTodoListById(int id) {
        return repository.getTodoListById(id);
    }


}
