package com.bank.transacciones.controller;

import com.bank.transacciones.dto.TransaccionRequestDTO;
import com.bank.transacciones.dto.TransaccionResponseDTO;
import com.bank.transacciones.service.TransaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

    private final TransaccionService transaccionService;

    @PostMapping
    public ResponseEntity<TransaccionResponseDTO> registrarTransaccion(@Valid @RequestBody TransaccionRequestDTO request) {
        return ResponseEntity.ok(transaccionService.ejecutarTransaccion(request));
    }

    @GetMapping("/historial/{numeroCuenta}")
    public ResponseEntity<List<TransaccionResponseDTO>> obtenerHistorial(@PathVariable("numeroCuenta") String numeroCuenta) {
        return ResponseEntity.ok(transaccionService.obtenerHistorial(numeroCuenta));
    }
}