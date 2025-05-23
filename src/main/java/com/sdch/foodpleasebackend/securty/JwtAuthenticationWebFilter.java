package com.sdch.foodpleasebackend.securty;

import com.sdch.foodpleasebackend.utils.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import reactor.core.publisher.Mono;

public class JwtAuthenticationWebFilter extends AuthenticationWebFilter {

  public JwtAuthenticationWebFilter(JwtUtil jwtUtil) {
    super(new JwtReactiveAuthenticationManager(jwtUtil));

    this.setServerAuthenticationConverter(
        exchange -> {
          String authHeader =
              exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

          if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
              String username = jwtUtil.getUsernameFromToken(token);
              if (username != null && jwtUtil.validateToken(token)) {
                return Mono.just(new UsernamePasswordAuthenticationToken(username, token, null));
              }
            } catch (Exception e) {
              return Mono.empty();
            }
          }

          return Mono.empty();
        });

    this.setAuthenticationSuccessHandler(
        (webFilterExchange, authentication) ->
            webFilterExchange.getChain().filter(webFilterExchange.getExchange()));

    this.setAuthenticationFailureHandler(
        (webFilterExchange, exception) ->
            Mono.fromRunnable(
                () ->
                    webFilterExchange
                        .getExchange()
                        .getResponse()
                        .setStatusCode(HttpStatus.UNAUTHORIZED)));
  }
}
