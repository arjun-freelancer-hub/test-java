package com.example.todoapi.controller;

import com.example.todoapi.dto.TodoRequest;
import com.example.todoapi.model.Todo;
import com.example.todoapi.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
public class TodoController {
    
    private final TodoService todoService;
    
    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }
    
    // Get all todos
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }
    
    // Get todo by ID
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        Optional<Todo> todo = todoService.getTodoById(id);
        return todo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Create new todo
    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody TodoRequest todoRequest) {
        Todo createdTodo = todoService.createTodo(todoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }
    
    // Update todo
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoRequest todoRequest) {
        Optional<Todo> updatedTodo = todoService.updateTodo(id, todoRequest);
        return updatedTodo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Update todo completion status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Todo> updateTodoStatus(@PathVariable Long id, @RequestParam boolean completed) {
        Optional<Todo> updatedTodo = todoService.updateTodoStatus(id, completed);
        return updatedTodo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Delete todo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        boolean deleted = todoService.deleteTodo(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Get todos by completion status
    @GetMapping("/status/{completed}")
    public ResponseEntity<List<Todo>> getTodosByStatus(@PathVariable boolean completed) {
        List<Todo> todos = todoService.getTodosByStatus(completed);
        return ResponseEntity.ok(todos);
    }
    
    // Search todos
    @GetMapping("/search")
    public ResponseEntity<List<Todo>> searchTodos(@RequestParam String q) {
        List<Todo> todos = todoService.searchTodos(q);
        return ResponseEntity.ok(todos);
    }
    
    // Get todos by title
    @GetMapping("/title")
    public ResponseEntity<List<Todo>> getTodosByTitle(@RequestParam String title) {
        List<Todo> todos = todoService.getTodosByTitle(title);
        return ResponseEntity.ok(todos);
    }
    
    // Get todos by description
    @GetMapping("/description")
    public ResponseEntity<List<Todo>> getTodosByDescription(@RequestParam String description) {
        List<Todo> todos = todoService.getTodosByDescription(description);
        return ResponseEntity.ok(todos);
    }
    
    // Get statistics
    @GetMapping("/statistics")
    public ResponseEntity<TodoService.TodoStatistics> getStatistics() {
        TodoService.TodoStatistics statistics = todoService.getStatistics();
        return ResponseEntity.ok(statistics);
    }
    
    // Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Todo API is running!");
    }
} 