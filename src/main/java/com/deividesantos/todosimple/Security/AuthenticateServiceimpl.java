package com.deividesantos.todosimple.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.deividesantos.todosimple.models.DTO.UserCreateDTO;
import com.deividesantos.todosimple.models.User;
import com.deividesantos.todosimple.repositories.UserRepository;
import com.deividesantos.todosimple.services.exception.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Service
public class AuthenticateServiceimpl implements AuthenticationService {

    @Value("${jwt.token.expiration}")
    Integer tokenExpiration;
    @Value("${jwt.refreshToken}")
    Integer refreshTokenExpiration;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=this.userRepository.findByUsername(username);
        if(Objects.isNull(user))
        throw new UsernameNotFoundException("Usuario não encontrado"+username);
        return new UserSpringSecurity(user);
    }

    @Override
    public TokenResponseDto obtertoken(UserCreateDTO objDto) {
         User user = userRepository.findByUsername(objDto.getUsername());
         if(user == null){
            throw new UsernameNotFoundException("Usuario não encontrado"+user);
         }
         UserSpringSecurity userSpringSecurity= new UserSpringSecurity(user);
        
        return TokenResponseDto
        .builder()
        .token(geratoken(userSpringSecurity,tokenExpiration))
        .refreshToken(geratoken(userSpringSecurity, refreshTokenExpiration))
        .build();
    }
    public String geratoken(UserSpringSecurity user,Integer ex){
        try{

            Algorithm algorithm= Algorithm.HMAC256("my-secret");


            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(geraDateExpirate(ex))
                    .sign(algorithm);

        }catch (JWTCreationException e){}
        throw new RuntimeException("Erro ao gerar Token");
    }
    public Instant geraDateExpirate(Integer expiration){
        return LocalDateTime.now()
                .plusHours(expiration)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    public String validaToken(String token){
        try{
            Algorithm algorithm= Algorithm.HMAC256("my-secret");

            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

        }catch (JWTVerificationException e){return "";}

    }

    public TokenResponseDto geraRefreshToken(String token) {
    try {
        Algorithm algorithm= Algorithm.HMAC256("my-secret");

        String username= JWT.require(algorithm)
                .withIssuer("auth-api")
                .build()
                .verify(token)
                .getSubject();

        User user = userRepository.findByUsername(username);
        if(user == null)
        throw new ObjectNotFoundException( "Usuario não encontrado"+ user);

        UserSpringSecurity userSpringSecurity= new UserSpringSecurity(user);
         var autentication = new UsernamePasswordAuthenticationToken(userSpringSecurity, null, userSpringSecurity.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(autentication);

        return TokenResponseDto
        .builder()
        .token(geratoken(userSpringSecurity, tokenExpiration))
        .refreshToken(geratoken(userSpringSecurity, refreshTokenExpiration))
        .build();
        

    } catch (JWTCreationException e) {
        throw new RuntimeException("Erro ao gerar Refresh Token");
    }
}


}
