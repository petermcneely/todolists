package com.todolist.todolist.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolist.todolist.datamodel.Todolist;
import com.todolist.todolist.datatransferobject.TodolistDTO;
import com.todolist.todolist.repository.TodolistRepository;

@Service
public class TodolistService {
    @Autowired
    private TodolistRepository todolistRepository;

    public List<TodolistDTO> getTodolists() {
        var dto = new ArrayList<TodolistDTO>();
        todolistRepository.findAll().forEach(t -> dto.add(new TodolistDTO(t)));
        return dto;
    }

    public TodolistDTO getTodolist(long id) {
        var resp = todolistRepository.findById(id);
        return resp.isPresent() ? new TodolistDTO(resp.get()) : null;
    }

    public TodolistDTO createTodolist(Todolist todolist) {
        var resp = todolistRepository.save(todolist);
        return new TodolistDTO(resp);
    }
    
    public TodolistDTO updateTodolist(Todolist todolist) {
        if (todolistRepository.existsById(todolist.getId())) {
            var resp = todolistRepository.save(todolist);
            return new TodolistDTO(resp);
        } else {
            return null;
        }
    }

    public void deleteTodolist(long id) {
        todolistRepository.deleteById(id);
    }
}
