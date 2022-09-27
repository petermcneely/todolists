package com.todolist.todolist.controller;

import com.todolist.todolist.datatransferobject.ItemDTO;
import com.todolist.todolist.service.ItemService;

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
@RequestMapping("/api/v1/todolists/{todolistid}/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping()
    public ResponseEntity<List<ItemDTO>> getItems(@PathVariable("todolistid") long todolistid) {
        var items = itemService.getItems(todolistid);
        return items == null ?
            new ResponseEntity<>(HttpStatus.NOT_FOUND) :
            new ResponseEntity<>(items, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItem(@PathVariable("todolistid") long todolistid, @PathVariable("id")  long id) {
        var item = itemService.getItem(todolistid, id);
        return item == null ?
            new ResponseEntity<>(HttpStatus.NOT_FOUND) :
            new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ItemDTO> createItem(@PathVariable("todolistid") long todolistid, @RequestBody String description) {
        var createdItem = itemService.createItem(todolistid, description);
        return createdItem == null ?
            new ResponseEntity<>(HttpStatus.NOT_FOUND) :
            new ResponseEntity<>(createdItem, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable("todolistid") long todolistid, @PathVariable("id") long id, @RequestBody String description) {
        var updatedItem = itemService.updateItem(todolistid, id, description);
        return updatedItem == null ?
            new ResponseEntity<>(HttpStatus.NOT_FOUND) :
            new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ItemDTO> deleteItem(@PathVariable("todolistid") long todolistid, @PathVariable("id")  long id) {
        itemService.deleteItem(todolistid, id);
        return new ResponseEntity<>(HttpStatus.OK);
    } 
}
