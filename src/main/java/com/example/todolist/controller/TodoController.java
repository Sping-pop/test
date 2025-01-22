package com.example.todolist.controller;

import com.example.todolist.model.Todo;
import com.example.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/get-todo")
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @PostMapping("/add-todo")
    public ResponseEntity<Todo> addTodo(@Valid @RequestBody Todo todo) {
        return ResponseEntity.ok(todoService.addTodo(todo));
    }

    @PostMapping("/update-todo/{id}")
    public ResponseEntity<Todo> updateTodoStatus(@PathVariable Long id) {
        Todo updatedTodo = todoService.updateTodoStatus(id);
        if (updatedTodo != null) {
            return ResponseEntity.ok(updatedTodo);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/del/{id}")
    public ResponseEntity<Boolean> deleteTodo(@PathVariable Long id) {
        boolean result = todoService.deleteTodo(id);
        if (result) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.notFound().build();
    }
} 