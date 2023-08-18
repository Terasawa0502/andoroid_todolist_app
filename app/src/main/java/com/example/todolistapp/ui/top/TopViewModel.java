package com.example.todolistapp.ui.top;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.todolistapp.data.entities.TodoSheet;
import com.example.todolistapp.data.repositories.ToDoListRepository;

import java.util.List;
import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TopViewModel extends ViewModel {

    private ToDoListRepository repository;

    @Inject
    TopViewModel(ToDoListRepository repository) {
        this.repository = repository;
    }

    public void insertTestSample(Context context) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                TodoSheet sampleData = new TodoSheet();
                sampleData.title = "サンプル3";
                repository.insertTodoSheet(sampleData);
                List<TodoSheet> datas = repository.getAllTodoSheet();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        TodoSheet sheet = datas.get(datas.size() -1);
                        Toast.makeText(context, "タイトル" + sheet.title,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }.start();
    }
}
