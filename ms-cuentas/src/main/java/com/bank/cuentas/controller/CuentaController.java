package com.bank.cuentas.controller;

import com.bank.cuentas.dto.CuentaRequestDTO;
import com.bank.cuentas.dto.CuentaResponseDTO;
import com.bank.cuentas.service.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @PostMapping
    public CuentaResponseDTO crearCuenta(@Valid @RequestBody CuentaRequestDTO request) {
        return cuentaService.crearCuenta(request);
    }

    @GetMapping
    public List<CuentaResponseDTO> listarCuentas() {
        return cuentaService.listarCuentas();
    }

    @GetMapping("/{id}")
    public CuentaResponseDTO buscarCuentaPorId(@PathVariable Long id) {
        return cuentaService.obtenerCuentaPorId(id);
    }

    @GetMapping("/buscar")
    public ResponseEntity<CuentaResponseDTO> obtenerCuentaPorNumero(@RequestParam("numeroCuenta") String numeroCuenta) {
        return ResponseEntity.ok(cuentaService.obtenerCuentaPorNumero(numeroCuenta));
    }

    @PutMapping("/actualizar-saldo")
    public ResponseEntity<Void> actualizarSaldo(
            @RequestBody com.bank.cuentas.dto.external.CuentaActualizarSaldoRequestDTO request) {
        cuentaService.actualizarSaldo(request.getNumeroCuenta(), request.getNuevoSaldo());
        return ResponseEntity.noContent().build();
    }
}