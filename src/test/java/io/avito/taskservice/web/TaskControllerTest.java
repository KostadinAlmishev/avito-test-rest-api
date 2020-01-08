package io.avito.taskservice.web;

import io.avito.taskservice.entity.Task;
import io.avito.taskservice.service.MapValidationErrorService;
import io.avito.taskservice.service.TaskService;
import io.avito.taskservice.web.entity.TaskListItemResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MapValidationErrorService mapValidationErrorService;
    @MockBean
    private TaskService taskService;

    @Test
    public void getTaskListPaginated_ShouldReturnFirstPage() throws Exception {
        List<Task> taskServiceReturnValues = new ArrayList<>();
        taskServiceReturnValues.add(new Task(null, "title1", "description1", 15, "path1", new ArrayList<>()));
        taskServiceReturnValues.add(new Task(null, "title2", "description2", 19, "path2", new ArrayList<>()));
        taskServiceReturnValues.add(new Task(null, "title3", "description3", 20, "path3", new ArrayList<>()));
        List<TaskListItemResponse> expectedResult = taskServiceReturnValues.stream()
                .map(TaskListItemResponse::new)
                .collect(Collectors.toList());

        given(taskService.findTasksPaginated(anyInt(), anyInt(), isNull(), isNull()))
                .willReturn(taskServiceReturnValues);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/task/list?page=0&size=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)));
    }
}