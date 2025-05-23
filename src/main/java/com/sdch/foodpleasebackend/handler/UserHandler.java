package com.sdch.foodpleasebackend.handler;

import com.sdch.foodpleasebackend.model.User;
import com.sdch.foodpleasebackend.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

  private final UserService userService;

  public UserHandler(UserService userService) {
    this.userService = userService;
  }

  public Mono<ServerResponse> me(ServerRequest request) {
    return ReactiveSecurityContextHolder.getContext()
        .map(ctx -> ctx.getAuthentication().getPrincipal())
        .flatMap(
            principal ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue("{\"user\":\"" + principal.toString() + "\"}"));
  }

  public Mono<ServerResponse> createUser(ServerRequest request) {
    return request
        .bodyToMono(User.class)
        .flatMap(userService::createUser)
        .flatMap(
            savedUser ->
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(savedUser))
        .onErrorResume(
            e -> ServerResponse.badRequest().bodyValue("{\"error\":\"" + e.getMessage() + "\"}"));
  }
}
