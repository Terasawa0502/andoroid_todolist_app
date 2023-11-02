package com.example.todolistapp.data.entities;

import java.util.List;

public class TodoSheetData {
    private int id;
    private String title;
    private List<TodoData> todoItemList;

    public TodoSheetData(int id, String title, List<TodoData> todoItemList) {
        this.id = id;
        this.title = title;
        this.todoItemList = todoItemList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TodoData> getTodoItemList() {
        return todoItemList;
    }

    public void setTodoItemList(List<TodoData> todoItemList) {
        this.todoItemList = todoItemList;
    }
}
