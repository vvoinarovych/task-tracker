package com.vvoinarovych.tasktrackerbackend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @GetMapping()
    ResponseEntity<String> getTasks() {
        return ResponseEntity.ok("Hello World");
    }
}
