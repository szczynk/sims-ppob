package com.szczynk.simsppob.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.szczynk.simsppob.model.request.LoginRequest;
import com.szczynk.simsppob.model.request.RegisterRequest;
import com.szczynk.simsppob.model.response.LoginResponse;
import com.szczynk.simsppob.model.response.WebResponse;
import com.szczynk.simsppob.service.AuthService;

import jakarta.validation.Valid;

@RestController
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public WebResponse<String> register(@Valid @RequestBody RegisterRequest request) {

        authService.register(request);

        return WebResponse.<String>builder()
                .status(0)
                .message("Registrasi berhasil silahkan login")
                .data(null)
                .build();
    }

    @PostMapping("/login")
    public WebResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return WebResponse.<LoginResponse>builder()
                .status(0)
                .message("Login Sukses")
                .data(response)
                .build();
    }
}
