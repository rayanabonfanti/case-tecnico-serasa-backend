package com.desafio.serasa.experian.domain.autenticador;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginResponseDto {
    private String token;
}
