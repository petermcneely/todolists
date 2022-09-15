package com.todolist.todolist.controller;

import com.todolist.todolist.datamodel.Todolist;
import com.todolist.todolist.datatransferobject.TodolistDTO;
import com.todolist.todolist.repository.TodolistRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/todolists")
public class TodolistController {

    @Autowired
    private TodolistRepository todolistRepository;

    @GetMapping()
    public ResponseEntity<List<TodolistDTO>> getTodolists() {
        var dto = new ArrayList<TodolistDTO>();
        todolistRepository.findAll().forEach(t -> dto.add(new TodolistDTO(t)));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TodolistDTO> getTodolist(long id) {
        var resp = todolistRepository.findById(id);
        if (resp == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new TodolistDTO(resp.get()), HttpStatus.OK);
        }
    }

    @PostMapping()
    public ResponseEntity<TodolistDTO> createTodolist(@RequestBody Todolist todolist) {
        var resp = todolistRepository.save(todolist);
        return new ResponseEntity<>(new TodolistDTO(resp), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodolistDTO> updateTodolist(@RequestBody Todolist todolist) {
        if (todolistRepository.existsById(todolist.getId())) {
            var resp = todolistRepository.save(todolist);
            return new ResponseEntity<>(new TodolistDTO(resp), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TodolistDTO> deleteTodolist(long id) {
        todolistRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    } 
}
