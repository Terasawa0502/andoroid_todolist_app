package com.example.todolistapp.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "todo")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "list_id")
    public int listId;

    @ColumnInfo(name = "todo_title")
    public String todoTitle;

    @ColumnInfo(name = "done_todo")
    public Boolean isDone;

    @ColumnInfo(name = "limited_at")
    public Date limitedAt;

    @ColumnInfo(name = "created_at")
    public Date createdAt;

    @ColumnInfo(name = "delete_flag")
    public Boolean deleteFlag;






}
