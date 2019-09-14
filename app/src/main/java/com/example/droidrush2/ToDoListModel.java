package com.example.droidrush2;

import androidx.annotation.NonNull;

public class ToDoListModel {
    String title;
    int id;
    String description;
    public ToDoListModel(int id, String title, String description){
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getTitle(){
        return this.title;
    }

    public  String getDescription(){
        return this.description;
    }

    public int getId(){
        return this.id;
    }
    public void setTitle(String  title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setId(int id){
        this.id = id;
    }
    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
