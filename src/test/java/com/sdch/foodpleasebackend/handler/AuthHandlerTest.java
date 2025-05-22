package com.sdch.foodpleasebackend.handler;

import com.sdch.foodpleasebackend.dto.AuthRequest;
import com.sdch.foodpleasebackend.model.User;
import com.sdch.foodpleasebackend.router.AuthRouter;
import com.sdch.foodpleasebackend.service.UserService;
import com.sdch.foodpleasebackend.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthHandlerTest {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        jwtUtil = Mockito.mock(JwtUtil.class);

        AuthHandler authHandler = new AuthHandler(userService, passwordEncoder, jwtUtil);
        AuthRouter authRouter = new AuthRouter();

        webTestClient = WebTestClient
                .bindToRouterFunction(authRouter.authRoutes(authHandler))
                .build();
    }

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() {
        User mockUser = new User();
        mockUser.setUsername("admin");
        mockUser.setPassword("hashed123");

        AuthRequest request = new AuthRequest();
        request.setUsername("admin");
        request.setPassword("secret123");

        when(userService.findByUsername("admin")).thenReturn(Mono.just(mockUser));
        when(passwordEncoder.matches("secret123", "hashed123")).thenReturn(true);
        when(jwtUtil.generateToken("admin")).thenReturn("mocked-token");

        webTestClient.post()
                .uri("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.token").isEqualTo("mocked-token");
    }

    @Test
    void login_shouldReturnBadRequest_whenCredentialsAreInvalid() {
        when(userService.findByUsername("admin")).thenReturn(Mono.empty());

        AuthRequest request = new AuthRequest();
        request.setUsername("admin");
        request.setPassword("wrong");

        webTestClient.post()
                .uri("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }
}