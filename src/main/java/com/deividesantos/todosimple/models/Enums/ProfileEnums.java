package com.deividesantos.todosimple.models.Enums;

import lombok.Getter;



@Getter
public enum ProfileEnums {

    ADMIN("ADMIN"),
    USER("USER");

    private String role;

    ProfileEnums(String role) {
        this.role = role;
    }
}
