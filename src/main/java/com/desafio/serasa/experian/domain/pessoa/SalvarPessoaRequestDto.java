package com.desafio.serasa.experian.domain.pessoa;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SalvarPessoaRequestDto {
    private String login;
    private String password;
    @Valid
    private PessoaRole role;
    @NotBlank(message = "cep is required.")
    @Pattern(regexp = "^\\d{8}$", message = "cep can only have numbers with size 8")
    private String cep;
    private String nome;
    private Integer idade;
    private String telefone;
    private Integer score;
}
