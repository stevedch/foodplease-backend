package com.sdch.foodpleasebackend.handler;

import com.sdch.foodpleasebackend.dto.AuthRequest;
import com.sdch.foodpleasebackend.dto.AuthResponse;
import com.sdch.foodpleasebackend.service.UserService;
import com.sdch.foodpleasebackend.utils.JwtUtil;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AuthHandler {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public AuthHandler(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  public Mono<ServerResponse> login(ServerRequest request) {
    return request
        .bodyToMono(AuthRequest.class)
        .flatMap(
            auth ->
                userService
                    .findByUsername(auth.getUsername())
                    .filter(user -> passwordEncoder.matches(auth.getPassword(), user.getPassword()))
                    .map(user -> jwtUtil.generateToken(user.getUsername()))
                    .map(AuthResponse::new)
                    .flatMap(
                        response ->
                            ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                    .switchIfEmpty(ServerResponse.badRequest().build()));
  }
}
