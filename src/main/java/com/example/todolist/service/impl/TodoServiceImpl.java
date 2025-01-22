package com.example.todolist.service.impl;

import com.example.todolist.mapper.TodoMapper;
import com.example.todolist.model.Todo;
import com.example.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoMapper todoMapper;

    @Override
    public List<Todo> getAllTodos() {
        return todoMapper.findAll();
    }

    @Override
    public Todo addTodo(Todo todo) {
        todo.setIsCompleted(false);
        todoMapper.insert(todo);
        return todo;
    }

    @Override
    public Todo updateTodoStatus(Long id) {
        Todo todo = todoMapper.findById(id);
        if (todo != null) {
            todo.setIsCompleted(!todo.getIsCompleted());
            todoMapper.updateStatus(todo);
            return todo;
        }
        return null;
    }

    @Override
    public boolean deleteTodo(Long id) {
        return todoMapper.deleteById(id) > 0;
    }
} 