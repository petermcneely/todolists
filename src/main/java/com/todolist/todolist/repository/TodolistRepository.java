package com.todolist.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todolist.todolist.datamodel.Todolist;

public interface TodolistRepository extends JpaRepository<Todolist, Long> {

}