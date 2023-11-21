package com.example.todolistapp.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import com.example.todolistapp.data.TodoDatabase;
import com.example.todolistapp.data.dao.TodoDao;
import com.example.todolistapp.data.dao.TodoSheetDao;
import com.example.todolistapp.data.entities.Todo;
import com.example.todolistapp.data.entities.TodoSheet;
import java.util.List;

public class ToDoListRepository {

    private TodoDatabase db;
    private TodoDao todoDao;
    private TodoSheetDao todoSheetDao;
    private LiveData<List<TodoSheet>> listTodoSheet;

    public ToDoListRepository(Context context) {
        db = Room.databaseBuilder(
                context, TodoDatabase.class,
                "todolist-db"
        ).build();
        todoDao = db.todoDao();
        todoSheetDao = db.todoSheetDao();
        listTodoSheet = todoSheetDao.getAll();
    }

    public void insertTodoItem(Todo item) {
        todoDao.insert(item);
    }

    public void insertTodoSheet(TodoSheet data) {
        todoSheetDao.insert(data);
    }

    public List<Todo> getTodoListById(int id) {
        return todoDao.getTodoListById(id);
    }
    public LiveData<List<TodoSheet>> getAllTodoSheet() {
        return listTodoSheet;
    }

    public void updateTodoData(Todo item) {
        todoDao.updateTodoData(item);
    }
}
