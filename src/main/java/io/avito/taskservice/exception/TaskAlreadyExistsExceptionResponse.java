package io.avito.taskservice.exception;

public class TaskAlreadyExistsExceptionResponse {

    private String taskAlreadyExists;

    public TaskAlreadyExistsExceptionResponse(String taskAlreadyExists) {
        this.taskAlreadyExists = taskAlreadyExists;
    }

    public String getTaskAlreadyExists() {
        return taskAlreadyExists;
    }

    public void setTaskAlreadyExists(String taskAlreadyExists) {
        this.taskAlreadyExists = taskAlreadyExists;
    }
}
