package com.sdch.foodpleasebackend.service;

import com.sdch.foodpleasebackend.model.User;
import com.sdch.foodpleasebackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Mono<User> createUser(User user) {
    if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
      return Mono.error(new IllegalArgumentException("Username cannot be blank"));
    }

    if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
      return Mono.error(new IllegalArgumentException("Password cannot be blank"));
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public Mono<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
