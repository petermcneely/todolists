package com.todolist.todolist.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

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
            var item = new Item();
            item.setTodolist(todolist.get());

            var dto = new ArrayList<ItemDTO>();
            itemRepository.findAll(Example.of(item)).forEach(i -> dto.add(new ItemDTO(i)));
            return dto;
        }
        return null;
    }

    public ItemDTO getItem(long todolistid, long id) {
        var resp = itemRepository.findById(id);
        return (resp == null || resp.get().getTodolist().getId() != todolistid) ?
            null :
            new ItemDTO(resp.get());
    }

    public ItemDTO createItem(long todolistid, Item item) {
        var resp = todolistRepository.findById(todolistid);
        if (resp.isPresent()) {
            item.setTodolist(resp.get());
            var savedItem = itemRepository.save(item);
            return new ItemDTO(savedItem);
        }
        return null;
    }

    public ItemDTO updateItem(Item item) {
        if (itemRepository.existsById(item.getId())) {
            var resp = itemRepository.save(item);
            return new ItemDTO(resp);
        }
        return null;
    }

    public void deleteItem(long id) {
        itemRepository.deleteById(id);
    }
}
