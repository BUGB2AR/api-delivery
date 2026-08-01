package com.food.api_delivery.controller;

import com.food.api_delivery.dto.request.CadastroUsuarioRequestDTO;
import com.food.api_delivery.dto.request.LoginRequestDTO;
import com.food.api_delivery.dto.response.AuthResponseDTO;
import com.food.api_delivery.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponseDTO cadastrar(@RequestBody CadastroUsuarioRequestDTO dto) {
        return authService.cadastrar(dto);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO dto) {
        return authService.login(dto);
    }
}