package org.example.restdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TodoDTO {

    private int id;
    private String title;
    private Boolean completed;
    private int userId;

    public int getId() {
        return id;
    }

    public TodoDTO setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TodoDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public TodoDTO setCompleted(Boolean completed) {
        this.completed = completed;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public TodoDTO setUserId(int userId) {
        this.userId = userId;
        return this;
    }
}
