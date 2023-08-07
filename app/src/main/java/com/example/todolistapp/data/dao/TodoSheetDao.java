package com.example.todolistapp.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.todolistapp.data.entities.TodoSheet;

import java.util.List;

@Dao
public interface TodoSheetDao {

    // onConflict 競合した場合の操作
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TodoSheet todoSheet);

    @Query("SELECT * FROM todo_sheet")
    List<TodoSheet> getAll();

    @Delete
    void delete(TodoSheet todoSheet);
}
