package io.avito.taskservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.avito.taskservice.entity.PictureLink;
import io.avito.taskservice.entity.Task;
import io.avito.taskservice.service.MapValidationErrorService;
import io.avito.taskservice.service.TaskService;
import io.avito.taskservice.web.entity.TaskListItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/task")
public class TaskController {

    private MapValidationErrorService mapValidationErrorService;
    private TaskService taskService;
    private ObjectMapper objectMapper;


    @Autowired
    public void setMapValidationErrorService(MapValidationErrorService mapValidationErrorService) {
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping("")
    public ResponseEntity<?> createTask(@Valid @RequestBody Task task, BindingResult bindingResult) {
        ResponseEntity<?> errMap =  mapValidationErrorService.map(bindingResult);
        if (errMap != null) {
            return errMap;
        }

        Task created = taskService.createTask(task);

        return new ResponseEntity<>(created.getId(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getTask(@RequestParam("id") long taskId,
                                     @RequestParam(value = "fields", required = false) List<String> fields) {
        Task task = taskService.findById(taskId);

        ObjectNode result = objectMapper.createObjectNode(); // For response mapping

        result.put("id", task.getId());
        result.put("title", task.getTitle());
        result.put("mainPictureLink", task.getMainPictureLink());
        result.put("price", task.getPrice());

        if (fields != null) {
            if (fields.contains("description")) {
                result.put("description", task.getDescription());
            }

            if (fields.contains("pictureLinks")) {
                ArrayNode pictureLinks = result.putArray("pictureLinks");

                List<String> plList = task.getPictureLinks();
                for (String pLink : plList) {
                    pictureLinks.add(pLink);
                }
            }
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getTaskListPaginated(@RequestParam("page") int page,
                                                  @RequestParam("size") int size,
                                                  @RequestParam(value = "sort_by", required = false) String sortBy,
                                                  @RequestParam(value = "ascending", required = false) Boolean ascending) {

        // Map Task to TaskListItemResponse
        List<TaskListItemResponse> tasks = taskService.findTasksPaginated(page, size, sortBy, ascending)
                .stream()
                .map(TaskListItemResponse::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
}
