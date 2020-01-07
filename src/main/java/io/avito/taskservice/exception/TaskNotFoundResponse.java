package io.avito.taskservice.exception;

public class TaskNotFoundResponse {

    private String taskNotFound;

    public TaskNotFoundResponse(String taskNotFound) {
        this.taskNotFound = taskNotFound;
    }

    public String getTaskNotFound() {
        return taskNotFound;
    }

    public void setTaskNotFound(String taskNotFound) {
        this.taskNotFound = taskNotFound;
    }

}
