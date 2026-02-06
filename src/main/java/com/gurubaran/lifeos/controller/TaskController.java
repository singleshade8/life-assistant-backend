package com.gurubaran.lifeos.controller;

import com.gurubaran.lifeos.model.Task;
import com.gurubaran.lifeos.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository repo;

    public TaskController(TaskRepository repo) {
        this.repo = repo;
    }

    // Create task
    @PostMapping
    public Task create(@RequestBody Task task) {
        return repo.save(task);
    }

    // Get all tasks
    @GetMapping
    public List<Task> getAll() {
        return repo.findAll();
    }

    // Mark task as completed
    @PutMapping("/{id}/complete")
    public Task markComplete(@PathVariable Long id) {
        Task task = repo.findById(id).orElseThrow();
        task.setCompleted(true);
        return repo.save(task);
    }

    // Check today's task status
    @GetMapping("/today/status")
    public String todayStatus() {
        LocalDate today = LocalDate.now();
        List<Task> tasks = repo.findByDueDate(today);

        if (tasks.isEmpty()) {
            return "No tasks scheduled for today 🎉";
        }

        boolean allDone = tasks.stream().allMatch(Task::isCompleted);

        if (allDone) {
            return "All of today's tasks are completed ✅";
        } else {
            long pending = tasks.stream().filter(t -> !t.isCompleted()).count();
            return "You still have " + pending + " task(s) pending today ⏳";
        }
    }
}
