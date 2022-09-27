package com.todolist.todolist.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todolist.todolist.datamodel.Item;
import com.todolist.todolist.datatransferobject.ItemDTO;
import com.todolist.todolist.repository.ItemRepository;
import com.todolist.todolist.repository.TodolistRepository;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private TodolistRepository todolistRepository;
    
    public List<ItemDTO> getItems(long todolistid) {
        var todolist = todolistRepository.findById(todolistid);
        if (todolist.isPresent()) {
            var dto = new ArrayList<ItemDTO>();
            todolist.get().getItems().forEach(i -> dto.add(new ItemDTO(i)));
            return dto;
        }
        return null;
    }

    public ItemDTO getItem(long todolistid, long id) {
        var resp = itemRepository.findById(id);
        return (!resp.isPresent() || resp.get().getTodolist().getId() != todolistid) ?
            null :
            new ItemDTO(resp.get());
    }

    @Transactional
    public ItemDTO createItem(long todolistid, String description) {
        var resp = todolistRepository.findById(todolistid);
        if (resp.isPresent()) {
            var item = new Item();
            item.setDescription(description);
            item.setTodolist(resp.get());
            var savedItem = itemRepository.save(item);
            return new ItemDTO(savedItem);
        }
        return null;
    }

    @Transactional
    public ItemDTO updateItem(long todolistid, long id, String description) {
        var resp = itemRepository.findById(id);
        if (resp.isPresent() && resp.get().getTodolist().getId() == todolistid) {
            var item = resp.get();
            item.setDescription(description);
            return new ItemDTO(itemRepository.save(item));
        }
        return null;
    }

    @Transactional
    public void deleteItem(long todolistid, long id) {
        var resp = itemRepository.findById(id);
        if (resp.isPresent() && resp.get().getTodolist().getId() == todolistid) {
            itemRepository.deleteById(id);
        }
    }
}
