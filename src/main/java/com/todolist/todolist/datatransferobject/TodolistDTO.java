package com.todolist.todolist.datatransferobject;

import java.util.ArrayList;
import java.util.List;

import com.todolist.todolist.datamodel.Todolist;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TodolistDTO {
    @Getter
    private long id;

    @Getter
    private String name;

    @Getter
    private List<ItemDTO> items;

    public TodolistDTO(Todolist todolist) {
        id = todolist.getId();
        name = todolist.getName();
        items = new ArrayList<ItemDTO>();
        todolist.getItems().forEach(i -> items.add(new ItemDTO(i)));
    }
}
