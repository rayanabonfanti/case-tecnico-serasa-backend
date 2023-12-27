//package com.desafio.serasa.experian.services;
//
//import com.desafio.serasa.experian.domain.user.Pessoa;
//import com.desafio.serasa.experian.exceptions.CustomException;
//import com.desafio.serasa.experian.repositories.UserRepository;
//import com.desafio.serasa.experian.domain.user.UserRole;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class AuthorizationServiceTest {
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private AuthorizationService authorizationService;
//
//    @Test
//    void loadUserByUsername_UserFound_ReturnsUserDetails() {
//        String username = "testUser";
//        String password = "testPassword";
//        UserRole role = UserRole.USER;
//
//        Pessoa mockPessoa = new Pessoa(username, password, role);
//        userRepository = mock(UserRepository.class);
//        when(userRepository.findByLogin(username)).thenReturn(mockPessoa);
//
//        authorizationService = new AuthorizationService(userRepository);
//        UserDetails userDetails = authorizationService.loadUserByUsername(username);
//
//        assertNotNull(userDetails);
//        assertEquals(username, userDetails.getUsername());
//        assertEquals(password, userDetails.getPassword());
//        verify(userRepository, times(1)).findByLogin(username);
//    }
//
//
//    @Test
//    void loadUserByUsername_UserNotFound_ThrowsException() {
//        String username = "nonexistentUser";
//        userRepository = mock(UserRepository.class);
//
//        when(userRepository.findByLogin(username)).thenReturn(null);
//
//        authorizationService = new AuthorizationService(userRepository);
//        CustomException exception = assertThrows(CustomException.class,
//                () -> authorizationService.loadUserByUsername(username));
//
//        assertEquals("User not found with username: " + username, exception.getMessage());
//        assertEquals(400, exception.getErrorCode());
//
//        verify(userRepository, times(1)).findByLogin(username);
//    }
//}
