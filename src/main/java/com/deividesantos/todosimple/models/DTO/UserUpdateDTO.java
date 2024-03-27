package com.deividesantos.todosimple.models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class UserUpdateDTO {

    private Long id;


    @Size(min=8,max=60)
    @NotBlank
    @NotEmpty
    @NotNull
    private String password;
}
