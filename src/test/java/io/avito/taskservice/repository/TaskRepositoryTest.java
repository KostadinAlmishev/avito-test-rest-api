package io.avito.taskservice.repository;

import io.avito.taskservice.entity.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void findById_ReturnsTask() {
        Task saved = testEntityManager.persistFlushFind(
                new Task(null, "title", "description", 123, "google.com", null)
        );

        assertThat(saved != null);

        Task task = taskRepository.findById(saved.getId()).orElse(null);

        assertThat(task != null);
        assertThat(task.getId().equals(saved.getId()));
        assertThat(task.getTitle().equals(saved.getTitle()));
        assertThat(task.getDescription().equals(saved.getDescription()));
        assertThat(task.getMainPictureLink().equals(saved.getMainPictureLink()));
        // TODO check list
    }

}