package com.food.api_delivery.exception;

import com.food.api_delivery.dto.request.ApiErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponseDTO handleBusinessException(BusinessException exception, HttpServletRequest request) {
        return new ApiErrorResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Regra invalida",
                exception.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler({PedidoNaoEncontradoException.class, ClienteNaoEncontradoException.class, EnderecoNaoEncontradoException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponseDTO handleNotFoundException(RuntimeException exception, HttpServletRequest request) {
        return new ApiErrorResponseDTO(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Recurso não encontrado",
                exception.getMessage(),
                request.getRequestURI()
        );
    }
}