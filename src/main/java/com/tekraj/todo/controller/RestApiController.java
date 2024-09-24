package com.tekraj.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tekraj.todo.model.Todo;
import com.tekraj.todo.repository.TodoRepository;

@RestController
@RequestMapping("/api")
public class RestApiController {
	 @Autowired
	    private TodoRepository todoRepository;

	    @GetMapping("")
	    public List<Todo> listTodos(Model model) {
	        List<Todo> todos = todoRepository.findAll();
	        return todos;
	    }

	   

	    @PostMapping("/store")
	    public Todo saveTodo(@ModelAttribute Todo todo) {
	        Todo result = todoRepository.save(todo);
	        return result;
	    }

	    

	    @PostMapping("/update/{id}")
	    public Todo updateTodo(@PathVariable Long id, @ModelAttribute Todo todo) {
	        todo.setId(id);
	        Todo updateResult = todoRepository.save(todo);
	        return updateResult;
	    }

	    @GetMapping("/delete/{id}")
	    public boolean deleteTodo(@PathVariable Long id) {
	        todoRepository.deleteById(id);
	        return true;
	    }

	    @GetMapping("/{id}")
	    public  Todo viewTodo(@PathVariable Long id, Model model) {
	        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + id));
	  
	        return todo;
	    }
}
