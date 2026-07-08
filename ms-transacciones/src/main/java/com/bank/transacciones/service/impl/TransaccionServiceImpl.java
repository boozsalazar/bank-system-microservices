package com.bank.transacciones.service.impl;

import com.bank.transacciones.client.CuentaFeignClient;
import com.bank.transacciones.dto.TransaccionRequestDTO;
import com.bank.transacciones.dto.TransaccionResponseDTO;
import com.bank.transacciones.dto.external.CuentaActualizarSaldoDTO;
import com.bank.transacciones.entity.Transaccion;
import com.bank.transacciones.entity.enums.TipoTransaccion;
import com.bank.transacciones.repository.TransaccionRepository;
import com.bank.transacciones.service.TransaccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransaccionServiceImpl implements TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final CuentaFeignClient cuentaFeignClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransaccionResponseDTO ejecutarTransaccion(TransaccionRequestDTO request) {

        // 1. Validar cuenta en ms-cuentas vía Feign
        Map<String, Object> cuentaMap;
        try {
            cuentaMap = cuentaFeignClient.obtenerCuentaPorNumero(request.getNumeroCuentaOrigen());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: La cuenta número " + request.getNumeroCuentaOrigen() + " no existe en el sistema.");
        }

        BigDecimal saldoActual = new BigDecimal(cuentaMap.get("saldo").toString());
        BigDecimal nuevoSaldo = BigDecimal.ZERO;

        // 2. Reglas de Negocio Bancario
        if (request.getTipoTransaccion() == TipoTransaccion.DEPOSITO) {
            nuevoSaldo = saldoActual.add(request.getMonto());

        } else if (request.getTipoTransaccion() == TipoTransaccion.RETIRO) {
            if (saldoActual.compareTo(request.getMonto()) < 0) {
                throw new IllegalArgumentException("Fondos insuficientes. Saldo disponible: $" + saldoActual);
            }
            nuevoSaldo = saldoActual.subtract(request.getMonto());
        } else {
            throw new IllegalArgumentException("Tipo de transacción no soportado aquí.");
        }

        // 3. Actualizar saldo remoto en ms-cuentas
        cuentaFeignClient.actualizarSaldo(CuentaActualizarSaldoDTO.builder()
                .numeroCuenta(request.getNumeroCuentaOrigen())
                .nuevoSaldo(nuevoSaldo)
                .build());

        // 4. Guardar registro histórico local inmutable
        Transaccion transaccion = Transaccion.builder()
                .numeroCuentaOrigen(request.getNumeroCuentaOrigen())
                .tipoTransaccion(request.getTipoTransaccion())
                .monto(request.getMonto())
                .fecha(LocalDateTime.now())
                .build();

        Transaccion guardada = transaccionRepository.save(transaccion);
        return mapToResponse(guardada);
    }

    @Override
    public List<TransaccionResponseDTO> obtenerHistorial(String numeroCuenta) {
        return transaccionRepository.findByNumeroCuentaOrigenOrNumeroCuentaDestinoOrderByFechaDesc(numeroCuenta, numeroCuenta)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private TransaccionResponseDTO mapToResponse(Transaccion t) {
        return TransaccionResponseDTO.builder()
                .id(t.getId())
                .numeroCuentaOrigen(t.getNumeroCuentaOrigen())
                .numeroCuentaDestino(t.getNumeroCuentaDestino())
                .tipoTransaccion(t.getTipoTransaccion())
                .monto(t.getMonto())
                .fecha(t.getFecha())
                .build();
    }
}