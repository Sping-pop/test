package com.example.todolist.controller;

import com.example.todolist.model.Todo;
import com.example.todolist.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllTodos() throws Exception {
        // 准备测试数据
        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setValue("测试待办事项1");
        todo1.setIsCompleted(false);

        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setValue("测试待办事项2");
        todo2.setIsCompleted(true);

        // 模拟service层的行为
        when(todoService.getAllTodos()).thenReturn(Arrays.asList(todo1, todo2));

        // 执行测试并验证结果
        mockMvc.perform(get("/api/get-todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].value").value("测试待办事项1"))
                .andExpect(jsonPath("$[0].isCompleted").value(false))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].value").value("测试待办事项2"))
                .andExpect(jsonPath("$[1].isCompleted").value(true));
    }

    @Test
    public void testAddTodo() throws Exception {
        // 准备测试数据
        Todo newTodo = new Todo();
        newTodo.setValue("新的待办事项");
        newTodo.setIsCompleted(false);

        Todo savedTodo = new Todo();
        savedTodo.setId(1L);
        savedTodo.setValue("新的待办事项");
        savedTodo.setIsCompleted(false);

        // 模拟service层的行为
        when(todoService.addTodo(any(Todo.class))).thenReturn(savedTodo);

        // 执行测试并验证结果
        mockMvc.perform(post("/api/add-todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTodo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.value").value("新的待办事项"))
                .andExpect(jsonPath("$.isCompleted").value(false));
    }

    @Test
    public void testUpdateTodoStatus() throws Exception {
        // 准备测试数据
        Todo updatedTodo = new Todo();
        updatedTodo.setId(1L);
        updatedTodo.setValue("待办事项");
        updatedTodo.setIsCompleted(true);

        // 模拟service层的行为
        when(todoService.updateTodoStatus(1L)).thenReturn(updatedTodo);

        // 执行测试并验证结果
        mockMvc.perform(post("/api/update-todo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.value").value("待办事项"))
                .andExpect(jsonPath("$.isCompleted").value(true));
    }

    @Test
    public void testDeleteTodo() throws Exception {
        // 模拟service层的行为
        when(todoService.deleteTodo(1L)).thenReturn(true);

        // 执行测试并验证结果
        mockMvc.perform(post("/api/del/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testAddTodoValidation() throws Exception {
        // 准备测试数据 - 空的待办事项
        Todo invalidTodo = new Todo();

        // 执行测试并验证结果
        mockMvc.perform(post("/api/add-todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidTodo)))
                .andExpect(status().isBadRequest());
    }
} 