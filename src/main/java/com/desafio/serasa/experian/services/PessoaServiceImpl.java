package com.desafio.serasa.experian.services;

import com.desafio.serasa.experian.domain.endereco.Endereco;
import com.desafio.serasa.experian.domain.endereco.EnderecoResponseApiDto;
import com.desafio.serasa.experian.domain.pessoa.AtualizarPessoaRequestDto;
import com.desafio.serasa.experian.domain.pessoa.Pessoa;
import com.desafio.serasa.experian.domain.pessoa.PessoaFilterDTO;
import com.desafio.serasa.experian.domain.pessoa.SalvarPessoaRequestDto;
import com.desafio.serasa.experian.exceptions.CustomException;
import com.desafio.serasa.experian.interfaces.PessoaService;
import com.desafio.serasa.experian.repositories.PessoaRepository;
import com.desafio.serasa.experian.utils.PessoaUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class PessoaServiceImpl implements PessoaService {

    private static final String OBJETO_NOT_FOUND = "Objeto não encontrado.";
    private final PessoaRepository pessoaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public Pessoa salvar(@Valid SalvarPessoaRequestDto data) throws CustomException {
        if (Optional.ofNullable(this.pessoaRepository.findByLogin(data.getLogin())).isPresent())
            PessoaUtils.retornarExcecao("Objeto já existente.");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        EnderecoResponseApiDto enderecoAPI = PessoaUtils.obterEnderecoPorCEP(data.getCep());

        if (Objects.isNull(enderecoAPI))
            PessoaUtils.retornarExcecao(OBJETO_NOT_FOUND);

        Pessoa newPessoa = PessoaUtils.criarPessoa(data, enderecoAPI, encryptedPassword);
        if (Objects.isNull(newPessoa)) {
            PessoaUtils.retornarExcecao(OBJETO_NOT_FOUND);
        } else {
            this.pessoaRepository.save(newPessoa);
            return newPessoa;
        }
        return null;
    }

    @Override
    public String deletar(String id) throws CustomException {
        Optional<Pessoa> findPessoa = pessoaRepository.findById(id);

        if (findPessoa.isPresent()) {
            Pessoa pessoa = findPessoa.get();
            pessoa.setDeleted(true);
            pessoaRepository.save(pessoa);

            return "Pessoa excluída logicamente com sucesso";
        }

        PessoaUtils.retornarExcecao(OBJETO_NOT_FOUND);
        return null;
    }

    @Override
    public Pessoa update(String id, @Valid AtualizarPessoaRequestDto data) throws CustomException {
        Optional<Pessoa> findPessoa = pessoaRepository.findById(id);

        if (findPessoa.isPresent()) {
            Pessoa pessoa = findPessoa.get();
            PessoaUtils.atualizarPessoaComDados(pessoa, data);
            pessoaRepository.save(pessoa);
            return pessoa;
        }

        PessoaUtils.retornarExcecao(OBJETO_NOT_FOUND);
        return null;
    }

    @Override
    public String getScoreStatus(@RequestParam String id) throws CustomException {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);

        if (pessoaOptional.isPresent()) {
            Pessoa pessoa = pessoaOptional.get();
            return PessoaUtils.obterDescricaoScore(pessoa.getScore());
        } else {
            PessoaUtils.retornarExcecao(OBJETO_NOT_FOUND);
        }
        return null;
    }

    @Override
    public Page<Pessoa> getPagedPeople(PessoaFilterDTO pessoaFilterDTO) {

        Pageable pageable = PageRequest.of(pessoaFilterDTO.getPage(), pessoaFilterDTO.getSize(),
                Sort.by(Sort.Direction.fromString(pessoaFilterDTO.getSortDirection()), pessoaFilterDTO.getSortField()));

        Pessoa examplePessoa = new Pessoa();
        examplePessoa.setNome(pessoaFilterDTO.getNome());
        examplePessoa.setIdade(pessoaFilterDTO.getIdade());
        Endereco exampleEndereco = new Endereco();
        exampleEndereco.setCep(pessoaFilterDTO.getCep());
        examplePessoa.setEndereco(exampleEndereco);

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Pessoa> example = Example.of(examplePessoa, matcher);

        return pessoaRepository.findAll(example, pageable);
    }

}
