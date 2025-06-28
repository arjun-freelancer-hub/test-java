package com.example.todoapi.repository;

import com.example.todoapi.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    
    // Find todos by completion status
    List<Todo> findByCompleted(boolean completed);
    
    // Find todos by title containing (case-insensitive)
    List<Todo> findByTitleContainingIgnoreCase(String title);
    
    // Find todos by description containing (case-insensitive)
    List<Todo> findByDescriptionContainingIgnoreCase(String description);
    
    // Custom query to find todos by title or description
    @Query("SELECT t FROM Todo t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(t.description) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Todo> findByTitleOrDescriptionContainingIgnoreCase(String searchTerm);
    
    // Count todos by completion status
    long countByCompleted(boolean completed);
} 