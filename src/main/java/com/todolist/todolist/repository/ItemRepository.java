package com.todolist.todolist.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.todolist.todolist.datamodel.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}