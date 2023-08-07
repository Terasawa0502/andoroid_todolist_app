package com.example.todolistapp.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.todolistapp.data.dao.TodoDao;
import com.example.todolistapp.data.dao.TodoSheetDao;
import com.example.todolistapp.data.entities.Todo;
import com.example.todolistapp.data.entities.TodoSheet;

@Database(entities = { Todo.class, TodoSheet.class}, exportSchema = false, version = 1)
@TypeConverters({Converter.class, DateTimeConverter.class})
public abstract class TodoDatabase extends RoomDatabase {
    public abstract TodoSheetDao todoSheetDao();
    public abstract TodoDao todoDao();
}
