package com.deividesantos.todosimple.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Entity
@Table(name=Task.NOME_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Task {
    public static final String NOME_TABLE= "task";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false,updatable = false)
    private User user;

    @Column(name="description",nullable = false,length = 255)
    @NotNull
    @NotEmpty
    @Size(min=1, max =255)
    private String description;

}
