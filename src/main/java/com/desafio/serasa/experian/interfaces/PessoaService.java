package com.desafio.serasa.experian.interfaces;

import com.desafio.serasa.experian.domain.pessoa.AtualizarPessoaRequestDto;
import com.desafio.serasa.experian.domain.pessoa.Pessoa;
import com.desafio.serasa.experian.domain.pessoa.SalvarPessoaRequestDto;
import com.desafio.serasa.experian.exceptions.CustomException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public interface PessoaService {

    Pessoa salvar(@Valid SalvarPessoaRequestDto data) throws CustomException;

    String deletar(String id) throws CustomException;

    Pessoa update(String id, @Valid AtualizarPessoaRequestDto data) throws CustomException;

    String getScoreStatus(@RequestParam String id) throws CustomException;

    Page<Pessoa> getPagedPeople(Integer page, Integer size, String sortField,
                                String sortDirection, String nome, Integer idade, String cep);
}
