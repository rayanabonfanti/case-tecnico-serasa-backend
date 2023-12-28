package com.desafio.serasa.experian.services;

import com.desafio.serasa.experian.domain.endereco.EnderecoResponseApiDto;
import com.desafio.serasa.experian.domain.pessoa.*;
import com.desafio.serasa.experian.exceptions.CustomException;
import com.desafio.serasa.experian.repositories.PessoaRepository;
import com.desafio.serasa.experian.utils.PessoaUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PessoaServiceImplTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private PessoaUtils pessoaUtils;

    @InjectMocks
    private PessoaServiceImpl pessoaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeletar() throws CustomException {
        when(pessoaRepository.findById(anyString())).thenReturn(Optional.of(criarPessoa()));
        when(pessoaRepository.save(any(Pessoa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String result = pessoaService.deletar("idExistente");

        assertEquals("Pessoa excluída logicamente com sucesso", result);
        verify(pessoaRepository, times(1)).findById("idExistente");
        verify(pessoaRepository, times(1)).save(any(Pessoa.class));
    }

    private EnderecoResponseApiDto criarEnderecoResponseApiDto() {
        return new EnderecoResponseApiDto("13035-070", "SP", "Vila Industrial", "Campinas", "Rua Caçapava");
    }

    private SalvarPessoaRequestDto criarSalvarPessoaRequestDto() {
        return new SalvarPessoaRequestDto("login", "password", PessoaRole.USER, "13035-070", "nome", 20, "11999780675", 345);
    }

    private AtualizarPessoaRequestDto criarAtualizarPessoaRequestDto() {
        return new AtualizarPessoaRequestDto("Novo Nome", 30, "11998887777", "54321-876");
    }

    private Pessoa criarPessoa() {
        return Pessoa.builder()
                .id(1L)
                .nome("Nome")
                .build();
    }

}
