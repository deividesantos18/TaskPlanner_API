package com.deividesantos.todosimple.controllers;

import com.deividesantos.todosimple.models.Task;
import com.deividesantos.todosimple.models.TaskProjection;
import com.deividesantos.todosimple.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> buscartasks(@PathVariable Long id){
        Task obj = this.taskService.findbyid(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/user")
    public ResponseEntity<List<TaskProjection>> findAllByUserid(){
        List<TaskProjection> obj = this.taskService.fidAllByUserid();
        return ResponseEntity.ok().body(obj);
    }


    @PostMapping
    @Validated
    public ResponseEntity<Void> enviartasks(@RequestBody@Valid Task obj){
        this.taskService.create(obj);
        URI uri= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> updateTasks(@PathVariable Long id,@RequestBody@Valid Task obj){
        obj.setId(id);
        this.taskService.update(obj);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTasks(@PathVariable Long id){
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
