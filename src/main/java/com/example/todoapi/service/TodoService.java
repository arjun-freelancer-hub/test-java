package com.example.todoapi.service;

import com.example.todoapi.dto.TodoRequest;
import com.example.todoapi.model.Todo;
import com.example.todoapi.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    
    private final TodoRepository todoRepository;
    
    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    
    // Get all todos
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
    
    // Get todo by ID
    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }
    
    // Create new todo
    public Todo createTodo(TodoRequest todoRequest) {
        Todo todo = new Todo(todoRequest.getTitle(), todoRequest.getDescription());
        return todoRepository.save(todo);
    }
    
    // Update todo
    public Optional<Todo> updateTodo(Long id, TodoRequest todoRequest) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setTitle(todoRequest.getTitle());
                    todo.setDescription(todoRequest.getDescription());
                    return todoRepository.save(todo);
                });
    }
    
    // Update todo completion status
    public Optional<Todo> updateTodoStatus(Long id, boolean completed) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setCompleted(completed);
                    return todoRepository.save(todo);
                });
    }
    
    // Delete todo
    public boolean deleteTodo(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Get todos by completion status
    public List<Todo> getTodosByStatus(boolean completed) {
        return todoRepository.findByCompleted(completed);
    }
    
    // Search todos by title or description
    public List<Todo> searchTodos(String searchTerm) {
        return todoRepository.findByTitleOrDescriptionContainingIgnoreCase(searchTerm);
    }
    
    // Get todos by title containing
    public List<Todo> getTodosByTitle(String title) {
        return todoRepository.findByTitleContainingIgnoreCase(title);
    }
    
    // Get todos by description containing
    public List<Todo> getTodosByDescription(String description) {
        return todoRepository.findByDescriptionContainingIgnoreCase(description);
    }
    
    // Get statistics
    public TodoStatistics getStatistics() {
        long totalTodos = todoRepository.count();
        long completedTodos = todoRepository.countByCompleted(true);
        long pendingTodos = todoRepository.countByCompleted(false);
        
        return new TodoStatistics(totalTodos, completedTodos, pendingTodos);
    }
    
    // Statistics class
    public static class TodoStatistics {
        private final long totalTodos;
        private final long completedTodos;
        private final long pendingTodos;
        
        public TodoStatistics(long totalTodos, long completedTodos, long pendingTodos) {
            this.totalTodos = totalTodos;
            this.completedTodos = completedTodos;
            this.pendingTodos = pendingTodos;
        }
        
        public long getTotalTodos() {
            return totalTodos;
        }
        
        public long getCompletedTodos() {
            return completedTodos;
        }
        
        public long getPendingTodos() {
            return pendingTodos;
        }
    }
} 