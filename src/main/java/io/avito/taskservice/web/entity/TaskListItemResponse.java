package io.avito.taskservice.web.entity;

import io.avito.taskservice.entity.Task;

public class TaskListItemResponse {

    private Long id;
    private String mainPictureLink;
    private int price;

    public TaskListItemResponse(Task task) {
        this.id = task.getId();
        this.mainPictureLink = task.getMainPictureLink();
        this.price = task.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMainPictureLink() {
        return mainPictureLink;
    }

    public void setMainPictureLink(String mainPictureLink) {
        this.mainPictureLink = mainPictureLink;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "TaskListItemResponse{" +
                "id=" + id +
                ", mainPicture='" + mainPictureLink + '\'' +
                ", price=" + price +
                '}';
    }
}
