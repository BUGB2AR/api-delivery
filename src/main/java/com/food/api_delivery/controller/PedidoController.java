package com.food.api_delivery.controller;

import com.food.api_delivery.dto.request.AtualizarStatusPedidoRequestDTO;
import com.food.api_delivery.dto.request.PedidoRequestDTO;
import com.food.api_delivery.dto.response.PedidoResponseDTO;
import com.food.api_delivery.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponseDTO criar(@RequestBody PedidoRequestDTO dto) {
        return pedidoService.criar(dto);
    }

    @PatchMapping("/{id}/status")
    public PedidoResponseDTO atualizarStatus(@PathVariable UUID id, @RequestBody AtualizarStatusPedidoRequestDTO dto) {
        return pedidoService.atualizarStatus(id, dto);
    }

    @GetMapping
    public List<PedidoResponseDTO> listarTodos() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    public PedidoResponseDTO buscarPorId(
            @PathVariable UUID id
    ) {
        return pedidoService.buscarPorId(id);
    }
}