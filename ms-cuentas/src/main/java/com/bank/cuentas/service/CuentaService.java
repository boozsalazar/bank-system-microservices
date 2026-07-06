package com.bank.cuentas.service;

import com.bank.cuentas.dto.CuentaRequestDTO;
import com.bank.cuentas.dto.CuentaResponseDTO;

import java.util.List;

public interface CuentaService {
    CuentaResponseDTO crearCuenta(CuentaRequestDTO request);
    List<CuentaResponseDTO> listarCuentas();
    CuentaResponseDTO obtenerCuentaPorId(Long cuentaId);
}
