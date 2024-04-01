package com.deividesantos.todosimple.models;

import com.deividesantos.todosimple.models.Enums.ProfileEnums;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.*;



@Entity
@Table(name=User.TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class User{
public static final String TABLE_NAME="user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",unique = true)
    private Long id;

    @Column(name = "username",length = 100,nullable = false,unique = true)
    @NotNull
    @NotEmpty
    @Size(min=2,max=100)
    private String username;

    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    @Column(name="password",length = 60,nullable = false)
    @NotNull
    @NotEmpty
    @Size(min=8,max=60)
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Task> tasks=new ArrayList<Task>();

    @Column(name="role",nullable = false)
    private ProfileEnums role;

}
