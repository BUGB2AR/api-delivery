package com.food.api_delivery.controller;

import com.food.api_delivery.dto.request.EnderecoRequestDTO;
import com.food.api_delivery.dto.response.EnderecoResponseDTO;
import com.food.api_delivery.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnderecoResponseDTO criar(@RequestBody EnderecoRequestDTO dto) {
        return service.criar(dto);
    }

    @GetMapping
    public List<EnderecoResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public EnderecoResponseDTO buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public EnderecoResponseDTO atualizar(@PathVariable UUID id, @RequestBody EnderecoRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id) {
        service.excluir(id);
    }
}