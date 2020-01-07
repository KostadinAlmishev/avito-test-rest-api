package io.avito.taskservice;

import io.avito.taskservice.entity.Task;
import io.avito.taskservice.service.TaskService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Component
class IntegrationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static TaskService taskService;
    @Autowired
    private TaskService tTaskService;

    // Tasks to be deleted after tests
    private static final List<Task> seededTasks = new ArrayList<>();
    private boolean isDataSeeded = false;

    @PostConstruct
    public void initStaticObj() {
        IntegrationTests.taskService = tTaskService;
    }

    @BeforeEach
    void setUp() {
        if (!isDataSeeded) {
            isDataSeeded = true;

            List<String> links = new ArrayList<>();
            links.add("link1");
            links.add("link2");
            links.add("link3");

            seededTasks.add(taskService.createTask(new Task(null, "title", "description", 15, "path", links)));
        }
    }

    @Test
    void createTask_ShouldCreateNewTaskAndReturnItsId() {
        Task task = new Task(null, "test", "description", 9, "path", null);
        ResponseEntity<Long> response = testRestTemplate.postForEntity(
                "/api/task",
                task,
                Long.class
        );

        Long responseId = response.getBody();

        Assertions.assertThat(response.getStatusCode() == HttpStatus.OK);
        Assertions.assertThat(responseId != null);

        task.setId(responseId);
        Task savedTask = taskService.findById(responseId);

        seededTasks.add(savedTask);
        Assertions.assertThat(task.equals(savedTask));
    }

    private void assertBasicTask(Task originalTask, Task responseTask) {
        Assertions.assertThat(originalTask).isNotEqualTo(null);
        Assertions.assertThat(responseTask).isNotEqualTo(null);

        Assertions.assertThat(responseTask.getId()).isEqualTo(originalTask.getId());
        Assertions.assertThat(responseTask.getTitle()).isEqualTo(originalTask.getTitle());
        Assertions.assertThat(responseTask.getPrice()).isEqualTo(originalTask.getPrice());
        Assertions.assertThat(responseTask.getMainPictureLink()).isEqualTo(originalTask.getMainPictureLink());
    }

    @Test
    void getSimpleTask() {
        Task originalTask = seededTasks.get(0);
        final String URL = String.format("/api/task?id=%d", originalTask.getId());

        ResponseEntity<Task> response = testRestTemplate.getForEntity(
                URL,
                Task.class
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Task responseTask = response.getBody();

        assertBasicTask(originalTask, responseTask);

        Assertions.assertThat(responseTask.getDescription()).isEqualTo(null);
        Assertions.assertThat(responseTask.getPictureLinks()).isEmpty();
    }

    @Test
    void getTaskWithDescription() {
        Task originalTask = seededTasks.get(0);
        final String URL = String.format("/api/task?id=%d&fields=description", originalTask.getId());

        ResponseEntity<Task> response = testRestTemplate.getForEntity(
                URL,
                Task.class
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Task responseTask = response.getBody();

        assertBasicTask(originalTask, responseTask);

        Assertions.assertThat(responseTask.getDescription()).isEqualTo(originalTask.getDescription());
        Assertions.assertThat(responseTask.getPictureLinks()).isEmpty();
    }

    @Test
    void getTaskWithPictureLinks() {
        Task originalTask = seededTasks.get(0);
        final String URL = String.format("/api/task?id=%d&fields=pictureLinks", originalTask.getId());

        ResponseEntity<Task> response = testRestTemplate.getForEntity(
                URL,
                Task.class
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Task responseTask = response.getBody();

        assertBasicTask(originalTask, responseTask);

        Assertions.assertThat(responseTask.getDescription()).isEqualTo(null);
        // Works with list
        Assertions.assertThat(responseTask.getPictureLinks()).isEqualTo(originalTask.getPictureLinks());
    }

    @Test
    void getTaskWithDescriptionAndPictureLinks() {
        Task originalTask = seededTasks.get(0);
        final String URL = String.format("/api/task?id=%d&fields=description&fields=pictureLinks", originalTask.getId());

        ResponseEntity<Task> response = testRestTemplate.getForEntity(
                URL,
                Task.class
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Task responseTask = response.getBody();

        assertBasicTask(originalTask, responseTask);

        Assertions.assertThat(responseTask.getDescription()).isEqualTo(originalTask.getDescription());
        // Works with list
        Assertions.assertThat(responseTask.getPictureLinks()).isEqualTo(originalTask.getPictureLinks());
    }

    @AfterAll
    static void afterAll() {
        for (Task task : seededTasks) {
            if (task != null && task.getId() != null) {
                taskService.deleteTask(task);
            }
        }
    }
}
