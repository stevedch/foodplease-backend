package com.sdch.foodpleasebackend.securty;

import com.sdch.foodpleasebackend.utils.JwtUtil;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {

  private final JwtUtil jwtUtil;

  public JwtReactiveAuthenticationManager(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String token = authentication.getCredentials().toString();
    String username = authentication.getPrincipal().toString();

    if (jwtUtil.validateToken(token) && username.equals(jwtUtil.getUsernameFromToken(token))) {
      return Mono.just(new UsernamePasswordAuthenticationToken(username, token, null));
    }

    return Mono.empty();
  }
}
