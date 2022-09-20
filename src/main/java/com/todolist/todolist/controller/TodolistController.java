package com.todolist.todolist.controller;

import com.todolist.todolist.datamodel.Todolist;
import com.todolist.todolist.datatransferobject.TodolistDTO;
import com.todolist.todolist.service.TodolistService;

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
    private TodolistService todolistService;

    @GetMapping()
    public ResponseEntity<List<TodolistDTO>> getTodolists() {
        var dto = todolistService.getTodolists();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TodolistDTO> getTodolist(long id) {
        var dto = todolistService.getTodolist(id);
        return dto == null ?
            new ResponseEntity<>(HttpStatus.NOT_FOUND) :
            new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<TodolistDTO> createTodolist(@RequestBody Todolist todolist) {
        var dto = todolistService.createTodolist(todolist);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodolistDTO> updateTodolist(@RequestBody Todolist todolist) {
        var dto = todolistService.updateTodolist(todolist);
        return dto == null ?
            new ResponseEntity<>(HttpStatus.NOT_FOUND) :
            new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TodolistDTO> deleteTodolist(long id) {
        todolistService.deleteTodolist(id);
        return new ResponseEntity<>(HttpStatus.OK);
    } 
}
