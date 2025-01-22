package com.example.todolist.service;

import com.example.todolist.model.Todo;
import java.util.List;

public interface TodoService {
    List<Todo> getAllTodos();
    Todo addTodo(Todo todo);
    Todo updateTodoStatus(Long id);
    boolean deleteTodo(Long id);
} 