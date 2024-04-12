package com.deividesantos.todosimple.models.DTO;

import com.deividesantos.todosimple.models.Enums.ProfileEnums;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class UserCreateDTO {

    @Size(min=2, max =100)
    @NotBlank
    @NotNull
    @NotNull
    private String username;

    @Size(min=8, max=60)
    @NotEmpty
    @NotBlank
    @NotNull
    private String password;

    @NotNull
    private ProfileEnums role;

}
