package org.example.restdemo.models;

public class Todo {
    private int id;
    private int userId;
    private String title;
    private boolean completed;

    public Todo() {}

    // Used for POST requests (create)
    public Todo(int userId, String title, boolean completed) {
        setUserId(userId);
        setTitle(title);
        setCompleted(completed);
    }

    // Used for GET and PUT (update) requests
    public Todo(int id, int userId, String title, boolean completed) {
        setId(id);
        setUserId(userId);
        setTitle(title);
        setCompleted(completed);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
