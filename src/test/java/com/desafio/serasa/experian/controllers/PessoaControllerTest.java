package com.desafio.serasa.experian.controllers;

import com.desafio.serasa.experian.domain.pessoa.AtualizarPessoaRequestDto;
import com.desafio.serasa.experian.domain.pessoa.Pessoa;
import com.desafio.serasa.experian.domain.pessoa.PessoaFilterDTO;
import com.desafio.serasa.experian.domain.pessoa.SalvarPessoaRequestDto;
import com.desafio.serasa.experian.exceptions.CustomException;
import com.desafio.serasa.experian.interfaces.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PessoaControllerTest {

    @InjectMocks
    private PessoaController pessoaController;

    @Mock
    private PessoaService pessoaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void healthcheck() {
        ResponseEntity<String> response = pessoaController.healthcheck();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("OK", response.getBody());
    }

    @Test
    void testSalvar() throws CustomException {
        SalvarPessoaRequestDto salvarPessoaRequestDto = new SalvarPessoaRequestDto();
        Pessoa pessoa = new Pessoa();
        when(pessoaService.salvar(any(SalvarPessoaRequestDto.class))).thenReturn(pessoa);

        ResponseEntity<Pessoa> responseEntity = pessoaController.salvar(salvarPessoaRequestDto, null);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(pessoa, responseEntity.getBody());
    }

    @Test
    void testDelete() throws CustomException {
        Long id = 1L;
        when(pessoaService.deletar(id)).thenReturn("Deleted");

        ResponseEntity<String> responseEntity = pessoaController.delete(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Deleted", responseEntity.getBody());
    }

    @Test
    void testUpdate() throws CustomException {
        Long id = 1L;
        AtualizarPessoaRequestDto atualizarPessoaRequestDto = new AtualizarPessoaRequestDto();
        Pessoa pessoa = new Pessoa();
        when(pessoaService.update(eq(id), any(AtualizarPessoaRequestDto.class))).thenReturn(pessoa);

        ResponseEntity<Pessoa> responseEntity = pessoaController.update(id, atualizarPessoaRequestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pessoa, responseEntity.getBody());
    }

    @Test
    void testGetScoreStatus() throws CustomException {
        Long id = 1L;
        when(pessoaService.getScoreStatus(id)).thenReturn("ScoreStatus");

        ResponseEntity<String> responseEntity = pessoaController.getScoreStatus(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("ScoreStatus", responseEntity.getBody());
    }

    @Test
    void testGetPagedPeople() {
        PessoaFilterDTO pessoaFilterDTO = new PessoaFilterDTO();
        Page<Pessoa> mockedPage = new PageImpl<>(Collections.singletonList(new Pessoa()));

        when(pessoaService.getPagedPeople(any(PessoaFilterDTO.class))).thenReturn(mockedPage);
        ResponseEntity<Page<Pessoa>> responseEntity = pessoaController.getPagedPeople(pessoaFilterDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockedPage, responseEntity.getBody());

        verify(pessoaService, times(1)).getPagedPeople(pessoaFilterDTO);
    }
}
