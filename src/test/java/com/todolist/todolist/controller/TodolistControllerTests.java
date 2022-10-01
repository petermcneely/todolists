package com.todolist.todolist.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.todolist.TodolistApplication;
import com.todolist.todolist.datamodel.Todolist;
import com.todolist.todolist.datatransferobject.TodolistDTO;
import com.todolist.todolist.service.TodolistService;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = TodolistApplication.class)
public class TodolistControllerTests {

    private static final String DUMMY_NAME = "test todo list";
    private static final long DUMMY_TODO_ID = 2;
    private static final Todolist DUMMY_TODO = Todolist.builder().id(DUMMY_TODO_ID).name(DUMMY_NAME).items(Collections.emptyList()).build();
    private static final TodolistDTO DUMMY_TODO_DTO = new TodolistDTO(DUMMY_TODO);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;  

    @MockBean
    private TodolistService mockTodolistService;

    private MockMvc mockMvc;
    
    @BeforeAll
    public void setUp() {
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void testCreateTodolist() throws Exception {
        Mockito.when(mockTodolistService.createTodolist(Mockito.anyString())).thenReturn(DUMMY_TODO_DTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/todolists")
                .content(DUMMY_NAME)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = OBJECT_MAPPER.writeValueAsString(DUMMY_TODO);

        assertEquals(expected, result.getResponse().getContentAsString());
        verify(mockTodolistService, times(1)).createTodolist(Mockito.anyString());
    }

    @Test
    public void testGetTodolists() throws Exception {
        Mockito.when(mockTodolistService.getTodolists()).thenReturn(Collections.singletonList(DUMMY_TODO_DTO));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/todolists");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = OBJECT_MAPPER.writeValueAsString(Collections.singletonList(DUMMY_TODO));

        assertEquals(expected, result.getResponse().getContentAsString());
        verify(mockTodolistService, times(1)).getTodolists();
    }

    @Test
    public void testGetTodolist() throws Exception {
        Mockito.when(mockTodolistService.getTodolist(Mockito.anyLong())).thenReturn(DUMMY_TODO_DTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/todolists/2");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
       
        String expected = OBJECT_MAPPER.writeValueAsString(DUMMY_TODO);

        assertEquals(expected, result.getResponse().getContentAsString());
        verify(mockTodolistService, times(1)).getTodolist(DUMMY_TODO_ID);
    }

    @Test
    public void testGetTodolist_NotFound() throws Exception {
        Mockito.when(mockTodolistService.getTodolist(Mockito.anyLong())).thenReturn(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1/todolists/2");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        verify(mockTodolistService, times(1)).getTodolist(DUMMY_TODO_ID);
    }

    @Test
    public void testUpdateTodolist() throws Exception {
        Mockito.when(mockTodolistService.updateTodolist(Mockito.anyLong(), Mockito.anyString())).thenReturn(DUMMY_TODO_DTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/todolists/2")
                .content(DUMMY_NAME)
                .contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
       
        String expected = OBJECT_MAPPER.writeValueAsString(DUMMY_TODO);

        assertEquals(expected, result.getResponse().getContentAsString());
        verify(mockTodolistService, times(1)).updateTodolist(Mockito.anyLong(), Mockito.anyString());
    }

    @Test
    public void testUpdateTodolist_NotFound() throws Exception {
        Mockito.when(mockTodolistService.updateTodolist(Mockito.anyLong(), Mockito.anyString())).thenReturn(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/todolists/2")
                .content(DUMMY_NAME)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        verify(mockTodolistService, times(1)).updateTodolist(Mockito.anyLong(), Mockito.anyString());
    }

    @Test
    public void testDeleteTodolist() throws Exception {
        Mockito.doNothing().when(mockTodolistService).deleteTodolist(Mockito.anyLong());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/todolists/2");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn(); 
        
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        verify(mockTodolistService, times(1)).deleteTodolist(DUMMY_TODO_ID);
    }
}
