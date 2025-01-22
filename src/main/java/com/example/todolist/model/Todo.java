package com.example.todolist.model;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Todo {
    private Long id;
    
    @NotBlank(message = "待办事项内容不能为空")
    private String value;
    
    @NotNull(message = "完成状态不能为空")
    private Boolean isCompleted;
} 