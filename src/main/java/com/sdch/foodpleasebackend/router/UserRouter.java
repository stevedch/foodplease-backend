package com.sdch.foodpleasebackend.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.sdch.foodpleasebackend.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

@Configuration
public class UserRouter {

  @Bean
  public RouterFunction<?> userRoutes(UserHandler userHandler) {
    return route(GET("/api/me"), userHandler::me);
  }
}
