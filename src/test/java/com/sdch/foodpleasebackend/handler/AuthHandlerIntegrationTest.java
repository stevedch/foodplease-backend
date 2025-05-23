package com.sdch.foodpleasebackend.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.sdch.foodpleasebackend.dto.AuthRequest;
import com.sdch.foodpleasebackend.dto.AuthResponse;
import com.sdch.foodpleasebackend.model.User;
import com.sdch.foodpleasebackend.router.AuthRouter;
import com.sdch.foodpleasebackend.router.UserRouter;
import com.sdch.foodpleasebackend.service.UserService;
import com.sdch.foodpleasebackend.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@ActiveProfiles("test")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration"
        })
@AutoConfigureWebTestClient
@Import({AuthHandler.class, UserHandler.class, AuthRouter.class, UserRouter.class, JwtUtil.class})
class AuthHandlerIntegrationTest {

  private static final String USERNAME = "admin";
  private static final String PASSWORD = "secret123";
  private static final String HASHED = "hashed-password";

  @Autowired private WebTestClient webTestClient;
  @MockitoBean private UserService userService;
  @MockitoBean private PasswordEncoder passwordEncoder;
  @Autowired private JwtUtil jwtUtil;

  @BeforeEach
  void setup() {
    reset(userService, passwordEncoder);
  }

  @Test
  void loginAndAccessProtectedRoute_shouldSucceed_withValidToken() {
    User user = new User();
    user.setUsername(USERNAME);
    user.setPassword(HASHED);

    when(userService.findByUsername(USERNAME)).thenReturn(Mono.just(user));
    when(passwordEncoder.matches(PASSWORD, HASHED)).thenReturn(true);

    AuthRequest request = new AuthRequest();
    request.setUsername(USERNAME);
    request.setPassword(PASSWORD);

    String token =
            webTestClient
                    .post()
                    .uri("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(AuthResponse.class)
                    .returnResult()
                    .getResponseBody()
                    .getToken();

    assertThat(token).isNotNull();

    webTestClient
            .get()
            .uri("/api/me")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.user")
            .isEqualTo(USERNAME);
  }

  @Test
  void login_shouldFail_ifUserNotFound() {
    when(userService.findByUsername(USERNAME)).thenReturn(Mono.empty());

    AuthRequest request = new AuthRequest();
    request.setUsername(USERNAME);
    request.setPassword(PASSWORD);

    webTestClient
            .post()
            .uri("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isUnauthorized()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.message").isEqualTo("User not found");
  }

  @Test
  void login_shouldFail_ifPasswordDoesNotMatch() {
    User user = new User();
    user.setUsername(USERNAME);
    user.setPassword(HASHED);

    when(userService.findByUsername(USERNAME)).thenReturn(Mono.just(user));
    when(passwordEncoder.matches(PASSWORD, HASHED)).thenReturn(false);

    AuthRequest request = new AuthRequest();
    request.setUsername(USERNAME);
    request.setPassword(PASSWORD);

    webTestClient
            .post()
            .uri("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isUnauthorized()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.message").isEqualTo("Invalid credentials");
  }

  @Test
  void accessProtectedRoute_shouldFail_ifTokenMissing() {
    webTestClient
            .get()
            .uri("/api/me")
            .exchange()
            .expectStatus().isUnauthorized();
  }

  @Test
  void accessProtectedRoute_shouldFail_ifTokenInvalid() {
    webTestClient
            .get()
            .uri("/api/me")
            .header(HttpHeaders.AUTHORIZATION, "Bearer faketoken123")
            .exchange()
            .expectStatus().isUnauthorized();
  }

  @Test
  void createUser_shouldSucceed_withValidInput() {
    String token = jwtUtil.generateToken(USERNAME);

    User userToCreate = new User();
    userToCreate.setUsername(USERNAME);
    userToCreate.setPassword(PASSWORD);

    User savedUser = new User();
    savedUser.setUsername(USERNAME);
    savedUser.setPassword("hashed-secret");

    when(userService.createUser(any(User.class))).thenReturn(Mono.just(savedUser));

    webTestClient
            .post()
            .uri("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .bodyValue(userToCreate)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.username").isEqualTo("admin")
            .jsonPath("$.password").isEqualTo("hashed-secret");
  }

  @Test
  void me_shouldSucceed_withValidToken() {
    String token = jwtUtil.generateToken(USERNAME);

    webTestClient
            .get()
            .uri("/api/me")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.user").isEqualTo(USERNAME);
  }
}