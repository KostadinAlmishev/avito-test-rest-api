package io.avito.taskservice.exception;

public class ResourceNotFoundExceptionResponse {

    private String resourceNotFound;

    public ResourceNotFoundExceptionResponse(String resourceNotFound) {
        this.resourceNotFound = resourceNotFound;
    }

    public String getResourceNotFound() {
        return resourceNotFound;
    }

    public void setResourceNotFound(String resourceNotFound) {
        this.resourceNotFound = resourceNotFound;
    }
}
