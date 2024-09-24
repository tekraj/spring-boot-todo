package com.tekraj.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tekraj.todo.model.Todo;
import com.tekraj.todo.repository.TodoRepository;

import java.util.List;

@Controller
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("")
    public String listTodos(Model model) {
        List<Todo> todos = todoRepository.findAll();
        model.addAttribute("todos", todos);
        return "todo/index";
    }

    @GetMapping("/create")
    public String createTodoForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "todo/form";
    }

    @PostMapping("/store")
    public String saveTodo(@ModelAttribute Todo todo) {
        todoRepository.save(todo);
        return "redirect:/todo";
    }

    @GetMapping("/edit/{id}")
    public String editTodoForm(@PathVariable Long id, Model model) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + id));
        model.addAttribute("todo", todo);
        return "todo/form";
    }

    @PostMapping("/update/{id}")
    public String updateTodo(@PathVariable Long id, @ModelAttribute Todo todo) {
        todo.setId(id);
        todoRepository.save(todo);
        return "redirect:/todo";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
        return "redirect:/todo";
    }

    @GetMapping("/{id}")
    public String viewTodo(@PathVariable Long id, Model model) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + id));
        model.addAttribute("todo", todo);
        return "view";
    }
}

