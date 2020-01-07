package io.avito.taskservice;

import io.avito.taskservice.entity.Task;
import io.avito.taskservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TaskServiceApplication {

    TaskService taskService ;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    /*
    @Bean
    CommandLineRunner init() {
        return args -> {
            taskService.createTask(new Task(null, "title", "description", 15, "path"));
            taskService.createTask(new Task(null, "title1", "description", 14, "path"));
            taskService.createTask(new Task(null, "title2", "description", 12, "path"));
            taskService.createTask(new Task(null, "title3", "description", 11, "path"));
            taskService.createTask(new Task(null, "title4", "description", 10, "path"));
            taskService.createTask(new Task(null, "title5", "description", 4, "path"));
            taskService.createTask(new Task(null, "title6", "description", 5, "path"));
            taskService.createTask(new Task(null, "title7", "description", 6, "path"));
            taskService.createTask(new Task(null, "title8", "description", 7, "path"));
            taskService.createTask(new Task(null, "title9", "description", 8, "path"));
            taskService.createTask(new Task(null, "title10", "description", 9, "path"));
        };
    }
     */

    public static void main(String[] args) {
        SpringApplication.run(TaskServiceApplication.class, args);
    }

}
