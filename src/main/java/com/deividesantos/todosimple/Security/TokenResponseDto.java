package com.deividesantos.todosimple.Security;

import lombok.Builder;

@Builder
public record TokenResponseDto(String token,String refreshToken) {
    
}
