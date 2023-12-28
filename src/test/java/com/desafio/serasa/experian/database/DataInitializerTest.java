//package com.desafio.serasa.experian.database;
//
//import com.desafio.serasa.experian.domain.endereco.Endereco;
//import com.desafio.serasa.experian.domain.pessoa.Pessoa;
//import com.desafio.serasa.experian.domain.pessoa.PessoaRole;
//import com.desafio.serasa.experian.repositories.PessoaRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.test.context.TestPropertySource;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest(classes = {DataInitializer.class})
//@TestPropertySource(properties = "password.admin=testPassword")
//class DataInitializerTest {
//    @InjectMocks
//    private DataInitializer dataInitializer;
//
//    @Mock
//    private PessoaRepository pessoaRepository;
//
//    @Mock
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @Test
//    void testRun() throws Exception {
//        MockitoAnnotations.openMocks(this);
//
//        // Definir comportamento esperado para o passwordEncoder
//        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
//
//        // Chamar o método run da classe DataInitializer
//        dataInitializer.run();
//
//        // Capturar o argumento enviado para o método save do PessoaRepository
//        ArgumentCaptor<Pessoa> pessoaCaptor = ArgumentCaptor.forClass(Pessoa.class);
//        verify(pessoaRepository, times(1)).save(pessoaCaptor.capture());
//
//        // Verificar se os dados da Pessoa estão corretos
//        Pessoa pessoaSalva = pessoaCaptor.getValue();
//        assertEquals("admin", pessoaSalva.getLogin());
//        assertEquals("hashedPassword", pessoaSalva.getPassword());
//        assertEquals(PessoaRole.ADMIN, pessoaSalva.getRole());
//        // Verificar outros atributos conforme necessário
//
//        // Outras verificações podem ser adicionadas com base na lógica de inicialização de dados
//    }
//}
