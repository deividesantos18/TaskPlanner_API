package com.deividesantos.todosimple.config;

import com.deividesantos.todosimple.Security.AuthenticationService;
import com.deividesantos.todosimple.Security.UserSpringSecurity;
import com.deividesantos.todosimple.models.User;
import com.deividesantos.todosimple.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    
    @Autowired
    AuthenticationService authenticateServiceimpl;    
    @Autowired
    UserRepository userRepository;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

       
        String token= extrairtokenhead(request);

        if (token!=null){
            String login= authenticateServiceimpl.validaToken(token);
            
            User user= userRepository.findByUsername(login);
            if(user == null){
            throw new UsernameNotFoundException("Usuario não encontrado"+login);
            }
            UserSpringSecurity userSpringSecurity= new UserSpringSecurity(user);
            
            var authentication= new UsernamePasswordAuthenticationToken(userSpringSecurity,null,userSpringSecurity.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }

    public String extrairtokenhead(HttpServletRequest httpServletRequest){
        var authheader= httpServletRequest.getHeader("Authorization");
        if (authheader==null){
            return null;
        }
        if(!authheader.split(" ")[0].equals("Bearer")){
            return  null;
        }
        return authheader.split(" ")[1];
    }

}
