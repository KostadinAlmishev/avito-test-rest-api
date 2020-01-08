package io.avito.taskservice.service;

import io.avito.taskservice.entity.PictureLink;
import io.avito.taskservice.exception.ResourceNotFoundException;
import io.avito.taskservice.exception.TaskAlreadyExistsException;
import io.avito.taskservice.exception.TaskNotFoundException;
import io.avito.taskservice.entity.Task;
import io.avito.taskservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private TaskRepository taskRepository;
    private PictureLinkService pictureLinkService;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    public void setPictureLinkService(PictureLinkService pictureLinkService) {
        this.pictureLinkService = pictureLinkService;
    }

    public Task createTask(Task task) {
        if (task.getId() != null && taskRepository.findById(task.getId()).orElse(null) != null) {
            throw new TaskAlreadyExistsException("Task already exists");
        }

        Task savedTask = taskRepository.save(task);

        if (task.getPictureLinks() != null) { // fixes null pointer exception in foreach
            for (String pl : task.getPictureLinks()) {
                pictureLinkService.savePictureLink(new PictureLink(null, pl, savedTask));
            }
        }

        return savedTask;
    }

    public Task findById(long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            throw new TaskNotFoundException("Task does not exists");
        }

        task.setPictureLinks(pictureLinkService.getAllPictureLinksByTask(task));

        return task;
    }

    public List<Task> findTasksPaginated(int page, int size, String sortBy, Boolean ascending) {

        Pageable pageRequest;

        if (!(sortBy == null || sortBy.isEmpty())) {
            if (!(sortBy.equals("price") || sortBy.equals("createdAt"))) {
                throw new TaskNotFoundException("Bad sort params");
            }

            Sort sort = Sort.by(sortBy);

            if (ascending == null || ascending) {
                sort = sort.ascending();
            } else {
                sort = sort.descending();
            }

            pageRequest = PageRequest.of(page, size, sort);
        } else {
            pageRequest = PageRequest.of(page, size);
        }

        Page<Task> res = taskRepository.findAll(pageRequest);

        if (page >= res.getTotalPages()) {
            throw new ResourceNotFoundException("Page not found");
        }

        return res.getContent();
    }

    public void deleteTask(Task task) {

        pictureLinkService.deletePictureLinksByTask(task); // Cascade constraints
        taskRepository.delete(task);
    }
}
