package com.deividesantos.todosimple.Security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.deividesantos.todosimple.models.User;
import com.deividesantos.todosimple.models.Enums.ProfileEnums;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class UserSpringSecurity implements UserDetails {
    
    private Long id;
    private String password;
    private String username;
    private ProfileEnums role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
     if(this.role== ProfileEnums.ADMIN){
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    

    public UserSpringSecurity(Long id, String password, String username, ProfileEnums role) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.role = role;
    }
    public UserSpringSecurity(User user){
        this.id=user.getId();
        this.username=user.getUsername();
        this.password=user.getPassword();
        this.role=user.getRole();
    }

    @Override
    public boolean isAccountNonExpired() {
    return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
    return true ;
    }

    @Override
    public boolean isEnabled() {
    return true ;
}

    
}
