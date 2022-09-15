package com.todolist.todolist.datatransferobject;

import com.todolist.todolist.datamodel.Item;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemDTO {
    @Getter
    private long id;

    @Getter
    private long todolistitemid;

    @Getter
    private String description;

    public ItemDTO(Item item) {
        this.id = item.getId();
        this.todolistitemid = item.getTodolist().getId();
        this.description = item.getDescription();
    }
}
