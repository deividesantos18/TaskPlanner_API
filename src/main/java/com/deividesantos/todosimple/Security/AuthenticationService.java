package com.deividesantos.todosimple.Security;

import com.deividesantos.todosimple.models.DTO.UserCreateDTO;


import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService extends UserDetailsService {
    public TokenResponseDto obtertoken(UserCreateDTO objDto);
    public String validaToken(String token);
    public TokenResponseDto geraRefreshToken(String token);
}
