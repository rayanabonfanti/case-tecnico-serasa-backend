package com.desafio.serasa.experian.controllers;

import com.desafio.serasa.experian.domain.autenticador.LoginRequestDto;
import com.desafio.serasa.experian.domain.autenticador.LoginResponseDto;
import com.desafio.serasa.experian.domain.pessoa.Pessoa;
import com.desafio.serasa.experian.repositories.PessoaRepository;
import com.desafio.serasa.experian.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void healthcheck() {
        ResponseEntity<String> response = authenticationController.healthcheck();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("OK", response.getBody());
    }

    @Test
    void testLogin() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("username", "password");
        Authentication authMock = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authMock);
        when(tokenService.generateToken(any())).thenReturn("mockedToken");

        ResponseEntity<LoginResponseDto> responseEntity = authenticationController.login(loginRequestDto);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("mockedToken", responseEntity.getBody().getToken());

        verify(authenticationManager, times(1)).authenticate(new UsernamePasswordAuthenticationToken("username", "password"));
    }
}
