package com.example.controller;

import com.example.model.Todo;
import com.example.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {

    @Autowired
    TodoRepository repo;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos() {
        List<Todo> todos = repo.findAll();
        if(todos.size()>0) {
            return new ResponseEntity<List<Todo>>(todos, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("No todos available", HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody Todo todos){
        todos.setCreatedAt(new Date(System.currentTimeMillis()));
        repo.save(todos);
        return new ResponseEntity<Todo>(todos, HttpStatus.OK);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getTodo(@PathVariable("id") String id){
        Optional<Todo> item = repo.findById(id);
        if(item.isPresent()) {
            return new ResponseEntity<>(item.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Todo not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> getTodo(@RequestBody Todo todo, @PathVariable("id") String id){
        Optional<Todo> item = repo.findById(id);
        if(item.isPresent()) {
            Todo todoToSave = item.get();
            todoToSave.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : todoToSave.getCompleted());
            todoToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoToSave.setTodo(todo.getTodo() !=null ? todo.getTodo() : todoToSave.getTodo());
            todoToSave.setDescription(todo.getDescription() != null ? todo.getDescription(): todoToSave.getDescription());
            repo.save(todoToSave);
            return new ResponseEntity<Todo>(todoToSave, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Todo you want to uodate not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("todos/{id}")
    public ResponseEntity<?> deleteTodoById(@PathVariable("id") String id){
        Optional<Todo> item = repo.findById(id);
        if(item.isPresent()) {
            Todo todoToDelete = item.get();
            repo.delete(todoToDelete);
            return new ResponseEntity<>("Todo has been deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Todo is already deleted in the database", HttpStatus.NOT_FOUND);
        }
    }

}
