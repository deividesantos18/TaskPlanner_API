package com.deividesantos.todosimple.controllers;

import com.deividesantos.todosimple.models.DTO.UserCreateDTO;
import com.deividesantos.todosimple.models.DTO.UserUpdateDTO;
import com.deividesantos.todosimple.models.User;
import com.deividesantos.todosimple.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

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
     

   

    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
    var obj =this.userService.findByAll();
    return ResponseEntity.ok(obj);
    }

    @PostMapping

    public ResponseEntity<Void> create(@RequestBody @Valid UserCreateDTO objdto) {
        User user = this.userService.fromDto(objdto);
        User newuser = this.userService.createUser(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newuser.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO objdto) {
        objdto.setId(id);
        User user = this.userService.fromDto(objdto);
        this.userService.updateuser(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
