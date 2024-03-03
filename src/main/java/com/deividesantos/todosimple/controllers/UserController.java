package com.deividesantos.todosimple.controllers;

import com.deividesantos.todosimple.models.User;
import com.deividesantos.todosimple.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> buscar(@PathVariable Long id){
       User obj = this.userService.findbyid(id);
        return ResponseEntity.ok().body(obj);
    }


    @PostMapping
    @Validated(User.CreatUser.class)
    public ResponseEntity<Void> create(@RequestBody@Valid User obj){
    this.userService.createUser(obj);
        URI uri= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
       return ResponseEntity.created(uri).build();
    }


    @PutMapping("/{id}")
    @Validated(User.UpdateUser.class)
    public ResponseEntity<Void> update(@PathVariable Long id,@RequestBody@Valid User obj){
        obj.setId(id);
        this.userService.updateuser(obj);
        return ResponseEntity.noContent().build();
    }

@DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
}



}
