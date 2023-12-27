package com.desafio.serasa.experian.domain.endereco;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "endereco")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "cep is required.")
    @Pattern(regexp = "^\\d{8}$", message = "cep can only have numbers with size 8")
    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String logradouro;
}
