package com.todolist.todolist.controller;

import com.todolist.todolist.datatransferobject.TodolistDTO;
import com.todolist.todolist.service.TodolistService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<TodolistDTO> getTodolist(@PathVariable("id") long id) {
        var dto = todolistService.getTodolist(id);
        return dto == null ?
            new ResponseEntity<>(HttpStatus.NOT_FOUND) :
            new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<TodolistDTO> createTodolist(@RequestBody String name) {
        var dto = todolistService.createTodolist(name);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodolistDTO> updateTodolist(@PathVariable("id") long id, @RequestBody String name) {
        var dto = todolistService.updateTodolist(id, name);
        return dto == null ?
            new ResponseEntity<>(HttpStatus.NOT_FOUND) :
            new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TodolistDTO> deleteTodolist(@PathVariable("id") long id) {
        todolistService.deleteTodolist(id);
        return new ResponseEntity<>(HttpStatus.OK);
    } 
}
