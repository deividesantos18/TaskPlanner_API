package com.deividesantos.todosimple.controllers;

import com.deividesantos.todosimple.Security.AuthenticationService;
import com.deividesantos.todosimple.models.DTO.UserCreateDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AutenticateController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String auth(@RequestBody UserCreateDTO objDto){
        
        var usuarioAutenticationToken= new UsernamePasswordAuthenticationToken(objDto.getUsername(),objDto
        .getPassword());
        try{
            authenticationManager.authenticate(usuarioAutenticationToken);
        }catch(Exception e){
            throw new UsernameNotFoundException("Usuario não encontrado");
        }
        
        return authenticationService.obtertoken(objDto);
    }

}
