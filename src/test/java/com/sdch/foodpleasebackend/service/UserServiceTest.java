package com.sdch.foodpleasebackend.service;

import static org.mockito.Mockito.*;

import com.sdch.foodpleasebackend.model.User;
import com.sdch.foodpleasebackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UserServiceTest {

  @Test
  void createUser_shouldHashPassword_andSaveUser() {
    // Arrange
    UserRepository mockRepo = mock(UserRepository.class);
    PasswordEncoder mockEncoder = mock(PasswordEncoder.class);
    UserService userService = new UserService(mockRepo, mockEncoder);

    User user = new User();
    user.setUsername("admin");
    user.setPassword("plain123");

    when(mockEncoder.encode("plain123")).thenReturn("hashed123");
    when(mockRepo.save(any(User.class)))
        .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

    // Act & Assert
    userService
        .createUser(user)
        .as(StepVerifier::create)
        .expectNextMatches(
            u -> u.getUsername().equals("admin") && u.getPassword().equals("hashed123"))
        .verifyComplete();
  }

  @Test
  void createUser_shouldFail_ifUsernameOrPasswordBlank() {
    UserRepository mockRepo = mock(UserRepository.class);
    PasswordEncoder mockEncoder = mock(PasswordEncoder.class);
    UserService userService = new UserService(mockRepo, mockEncoder);

    User invalidUser = new User();
    invalidUser.setUsername("   "); // en blanco
    invalidUser.setPassword("  "); // en blanco

    userService
        .createUser(invalidUser)
        .as(StepVerifier::create)
        .expectErrorMatches(
            err -> err instanceof IllegalArgumentException && err.getMessage().contains("Username"))
        .verify();
  }
}
