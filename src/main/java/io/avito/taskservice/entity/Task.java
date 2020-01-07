package io.avito.taskservice.entity;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 200, message = "Title max length is 200")
    private String title;

    @Size(max = 1000, message = "Description max length is 1000")
    private String description;

    private int price;

    private String mainPictureLink;

    // Max 3 photos
    @Transient
    @Size(max = 3, message = "Task can have maximum 3 pictures")
    private List<String> pictureLinks = new ArrayList<>();

    private Date createdAt;

    public Task() {
    }

    public Task(Long id, String title, String description, int price, String mainPictureLink, List<String> pictureLinks) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.mainPictureLink = mainPictureLink;
        this.pictureLinks = pictureLinks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(Integer priceInCents) {
        this.price = priceInCents;
    }

    public String getMainPictureLink() {
        return mainPictureLink;
    }

    public void setMainPictureLink(String mainPhotoPath) {
        this.mainPictureLink = mainPhotoPath;
    }

    public List<String> getPictureLinks() {
        return pictureLinks;
    }

    public void setPictureLinks(List<String> pictureLinks) {
        this.pictureLinks = pictureLinks;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = new Date();
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", mainPictureLink='" + mainPictureLink + '\'' +
                ", pictureLinks=" + pictureLinks +
                '}';
    }
}
