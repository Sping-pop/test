package com.example.todolist.mapper;

import com.example.todolist.model.Todo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TodoMapper {
    
    @Select("SELECT * FROM list")
    List<Todo> findAll();
    
    @Insert("INSERT INTO list(value, is_completed) VALUES(#{value}, #{isCompleted})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Todo todo);
    
    @Update("UPDATE list SET is_completed = #{isCompleted} WHERE id = #{id}")
    int updateStatus(Todo todo);
    
    @Delete("DELETE FROM list WHERE id = #{id}")
    int deleteById(Long id);
    
    @Select("SELECT * FROM list WHERE id = #{id}")
    Todo findById(Long id);
} 