package io.avito.taskservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class PictureLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Link should not be blank")
    private String link;

    @ManyToOne
    @JoinColumn(name = "task_id", updatable = false, nullable = false)
    @JsonIgnore
    private Task task;

    public PictureLink() {
    }

    public PictureLink(Long id, String link, Task task) {
        this.id = id;
        this.link = link;
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "PictureLink{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", task=" + task +
                '}';
    }
}
