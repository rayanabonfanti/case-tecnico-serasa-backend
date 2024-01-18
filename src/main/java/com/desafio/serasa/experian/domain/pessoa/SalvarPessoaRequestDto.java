package com.desafio.serasa.experian.domain.pessoa;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SalvarPessoaRequestDto {
    @NotBlank(message = "login is required.")
    private String login;
    @NotBlank(message = "password is required.")
    private String password;
    @Valid
    private PessoaRole role;
    @NotBlank(message = "cep is required.")
    private String cep;
    private String nome;
    private Integer idade;
    private String telefone;
    @NotNull(message = "score is required.")
    @Max(value = 1000, message = "value max is 1000 score.")
    private Integer score;
}
