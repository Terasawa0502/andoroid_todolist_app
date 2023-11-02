package com.example.todolistapp.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.util.Date;

public class TodoData {

    private int id;

    private int listId;

    private String todoTitle;

    private Boolean isDone;

    private Date limitedAt;

    private Date createdAt;

    private Boolean deleteFlag;

    public TodoData(int id, int listId, String todoTitle, Boolean isDone, Date limitedAt, Date createdAt, Boolean deleteFlag) {
        this.id = id;
        this.listId = listId;
        this.todoTitle = todoTitle;
        this.isDone = isDone;
        this.limitedAt = limitedAt;
        this.createdAt = createdAt;
        this.deleteFlag = deleteFlag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public Date getLimitedAt() {
        return limitedAt;
    }

    public void setLimitedAt(Date limitedAt) {
        this.limitedAt = limitedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
