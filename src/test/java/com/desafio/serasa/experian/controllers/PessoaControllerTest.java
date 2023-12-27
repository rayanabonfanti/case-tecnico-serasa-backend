//package com.desafio.serasa.experian.controllers;
//
//import com.desafio.serasa.experian.domain.user.RegisterDTO;
//import com.desafio.serasa.experian.domain.user.Pessoa;
//import com.desafio.serasa.experian.domain.user.UserRole;
//import com.desafio.serasa.experian.repositories.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class PessoaControllerTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserController userController;
//
//    @Test
//    public void healthcheck_ReturnsOk() {
//        ResponseEntity response = userController.healthcheck();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("OK", response.getBody());
//    }
//
//    @Test
//    public void register_NewUser_ReturnsOk() {
//        RegisterDTO registerDTO = new RegisterDTO("newuser", "password", UserRole.USER);
//        when(userRepository.findByLogin(registerDTO.login())).thenReturn(null);
//
//        ResponseEntity response = userController.register(registerDTO);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(userRepository, times(1)).save(any(Pessoa.class));
//    }
//
//    @Test
//    public void register_ExistingUser_ReturnsBadRequest() {
//        RegisterDTO registerDTO = new RegisterDTO("existinguser", "password", UserRole.USER);
//        when(userRepository.findByLogin(registerDTO.login())).thenReturn(new Pessoa());
//
//        ResponseEntity response = userController.register(registerDTO);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        verify(userRepository, never()).save(any(Pessoa.class));
//    }
//}
