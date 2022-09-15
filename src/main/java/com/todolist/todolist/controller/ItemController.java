package com.todolist.todolist.controller;

import com.todolist.todolist.datamodel.Item;
import com.todolist.todolist.datatransferobject.ItemDTO;
import com.todolist.todolist.repository.ItemRepository;
import com.todolist.todolist.repository.TodolistRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
@RequestMapping("/api/v1/todolists/{todolistitemid}/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private TodolistRepository todolistRepository;

    @GetMapping()
    public ResponseEntity<List<ItemDTO>> getItems(@PathVariable("todolistitemid") long todolistitemid) {
        var resp = todolistRepository.findById(todolistitemid);
        if (resp == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var item = new Item();
        item.setTodolist(resp.get());

        var dto = new ArrayList<ItemDTO>();
        itemRepository.findAll(Example.of(item)).forEach(i -> dto.add(new ItemDTO(i)));

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItem(@PathVariable("todolistitemid") long todolistitemid, long id) {
        var resp = itemRepository.findById(id);
        if (resp == null || resp.get().getTodolist().getId() != todolistitemid) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new ItemDTO(resp.get()), HttpStatus.OK);
        }
    }

    @PostMapping()
    public ResponseEntity<ItemDTO> createItem(@PathVariable("todolistitemid") long todolistitemid, @RequestBody Item item) {
        var resp = todolistRepository.findById(todolistitemid);
        if (resp == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        item.setTodolist(resp.get());
        var savedItem = itemRepository.save(item);
        return new ResponseEntity<>(new ItemDTO(savedItem), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> updateItem(@RequestBody Item item) {
        if (itemRepository.existsById(item.getId())) {
            var resp = itemRepository.save(item);
            return new ResponseEntity<>(new ItemDTO(resp), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ItemDTO> deleteItem(long id) {
        itemRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    } 
}
