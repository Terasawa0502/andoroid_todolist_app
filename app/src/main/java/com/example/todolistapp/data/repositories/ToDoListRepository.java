package com.example.todolistapp.data.repositories;

import android.content.Context;
import androidx.room.Room;
import com.example.todolistapp.data.TodoDatabase;
import com.example.todolistapp.data.dao.TodoSheetDao;
import com.example.todolistapp.data.entities.TodoSheet;
import java.util.List;

public class ToDoListRepository {

    private TodoDatabase db;
    private TodoSheetDao todoSheetDao;

    public ToDoListRepository(Context context) {
        db = Room.databaseBuilder(
                context, TodoDatabase.class,
                "todolist-db"
        ).build();
        todoSheetDao = db.todoSheetDao();
    }

    public void insertTodoSheet(TodoSheet data) {
        todoSheetDao.insert(data);
    }

    public List<TodoSheet> getAllTodoSheet() {
        return todoSheetDao.getAll();
    }
}
