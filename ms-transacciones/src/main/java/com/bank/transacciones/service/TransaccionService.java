package com.bank.transacciones.service;

import com.bank.transacciones.dto.TransaccionRequestDTO;
import com.bank.transacciones.dto.TransaccionResponseDTO;
import java.util.List;

public interface TransaccionService {
    TransaccionResponseDTO ejecutarTransaccion(TransaccionRequestDTO request);
    List<TransaccionResponseDTO> obtenerHistorial(String numeroCuenta);
}