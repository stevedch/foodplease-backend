package com.sdch.foodpleasebackend.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.sdch.foodpleasebackend.handler.AuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

@Configuration
public class AuthRouter {

  @Bean
  public RouterFunction<?> authRoutes(AuthHandler authHandler) {
    return route(POST("/auth/login"), authHandler::login);
  }
}
