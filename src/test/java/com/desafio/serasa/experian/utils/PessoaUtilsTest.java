package com.desafio.serasa.experian.utils;

import com.desafio.serasa.experian.domain.endereco.EnderecoResponseApiDto;
import com.desafio.serasa.experian.domain.pessoa.AtualizarPessoaRequestDto;
import com.desafio.serasa.experian.domain.pessoa.Pessoa;
import com.desafio.serasa.experian.domain.pessoa.SalvarPessoaRequestDto;
import com.desafio.serasa.experian.exceptions.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PessoaUtilsTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PessoaUtils pessoaUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObterEnderecoPorCEP() {
        String cep = "13035070";
        EnderecoResponseApiDto endereco = new EnderecoResponseApiDto("13035-070", "SP", "Vila Industrial", "Campinas", "Rua Caçapava");

        EnderecoResponseApiDto result = PessoaUtils.obterEnderecoPorCEP(cep);

        assertEquals(endereco, result);
    }

    @Test
    void testObterDescricaoScore() {
        assertEquals("Insuficiente", pessoaUtils.obterDescricaoScore(150));
        assertEquals("Inaceitável", pessoaUtils.obterDescricaoScore(350));
        assertEquals("Aceitável", pessoaUtils.obterDescricaoScore(600));
        assertEquals("Recomendável", pessoaUtils.obterDescricaoScore(800));
        assertEquals("Score fora da faixa", pessoaUtils.obterDescricaoScore(1200));
    }

    @Test
    void testCriarPessoa() {
        SalvarPessoaRequestDto data = new SalvarPessoaRequestDto();
        data.setLogin("teste");
        data.setCep("13035070");
        data.setScore(100);
        EnderecoResponseApiDto enderecoAPI = new EnderecoResponseApiDto();
        String encryptedPassword = "encryptedPassword";

        Pessoa result = PessoaUtils.criarPessoa(data, enderecoAPI, encryptedPassword);

        assertEquals(data.getLogin(), result.getLogin());
        assertEquals(data.getRole(), result.getRole());
        assertEquals(enderecoAPI.getCep(), result.getEndereco().getCep());
    }

    @Test
    void testAtualizarPessoaComDados() throws CustomException {
        Pessoa pessoa = new Pessoa();
        AtualizarPessoaRequestDto data = new AtualizarPessoaRequestDto();
        data.setCep("13035-070");
        EnderecoResponseApiDto enderecoAPI = new EnderecoResponseApiDto();
        enderecoAPI.setCep("13035-070");
        when(restTemplate.getForObject(anyString(), eq(EnderecoResponseApiDto.class))).thenReturn(enderecoAPI);

        PessoaUtils.atualizarPessoaComDados(pessoa, data);

        assertEquals(data.getNome(), pessoa.getNome());
        assertEquals(data.getIdade(), pessoa.getIdade());
        assertEquals(data.getTelefone(), pessoa.getTelefone());
        assertEquals(enderecoAPI.getCep(), pessoa.getEndereco().getCep());
    }

}
