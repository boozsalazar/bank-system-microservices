package com.bank.cuentas.service.impl;

import com.bank.cuentas.client.ClienteFeignClient; // Importamos el cliente Feign
import com.bank.cuentas.dto.CuentaRequestDTO;
import com.bank.cuentas.dto.CuentaResponseDTO;
import com.bank.cuentas.entity.Cuenta;
import com.bank.cuentas.repository.CuentaRepository;
import com.bank.cuentas.service.CuentaService;
import feign.FeignException; // Importación para capturar el 404 de Feign
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaServicesImpl implements CuentaService {

    private static final String PREFIJO_CUENTA = "001-";
    private final CuentaRepository cuentaRepository;
    private final ClienteFeignClient clienteFeignClient; // 🔥 Inyectamos el puente inter-microservicio

    @Override
    @Transactional
    public CuentaResponseDTO crearCuenta(CuentaRequestDTO request) {

        // 1. VALIDACIÓN INTER-MICROSERVICIO: Verificamos si el cliente existe en ms-clientes
        try {
            clienteFeignClient.obtenerPorId(request.getClienteId());
        } catch (FeignException.NotFound e) {
            // Si ms-clientes responde 404, lanzamos un error de negocio claro
            throw new IllegalArgumentException("Error: El cliente con ID " + request.getClienteId() + " no existe en el sistema bancario.");
        } catch (Exception e) {
            // Por si el microservicio de clientes está apagado o hay un problema de red
            throw new RuntimeException("Error de comunicación con el servicio de clientes: " + e.getMessage());
        }

        // 2. VALIDACIÓN LOCAL: Evitamos que el mismo cliente tenga dos cuentas del mismo tipo
        if (cuentaRepository.existsByClienteIdAndTipoCuenta(request.getClienteId(), request.getTipoCuenta())) {
            throw new IllegalArgumentException("El cliente ya tiene una cuenta de tipo " + request.getTipoCuenta());
        }

        // 3. GENERACIÓN DEL NÚMERO DE CUENTA (Lógica de negocio en memoria)
        // Optimizamos: Generamos un número único de 8 dígitos aleatorios antes de guardar,
        // eliminando la dependencia del ID secuencial de la base de datos.
        String numeroCuentaGenerado = PREFIJO_CUENTA + String.format("%08d", (int)(Math.random() * 100000000));

        Cuenta cuenta = Cuenta.builder()
                .clienteId(request.getClienteId())
                .tipoCuenta(request.getTipoCuenta())
                .numeroCuenta(numeroCuentaGenerado) // Seteado de inmediato
                .saldo(BigDecimal.ZERO)
                .activa(true)
                .build();

        // 4. PERSISTENCIA OPTIMIZADA: Una sola llamada de escritura (1 solo INSERT)
        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);

        return mapToResponse(cuentaGuardada);
    }

    @Override
    public List<CuentaResponseDTO> listarCuentas() {
        return cuentaRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public CuentaResponseDTO obtenerCuentaPorId(Long id) {
        Cuenta cuenta = cuentaRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
        return mapToResponse(cuenta);
    }

    public CuentaResponseDTO mapToResponse(Cuenta cuenta) {
        return CuentaResponseDTO.builder()
                .id(cuenta.getId())
                .clienteId(cuenta.getClienteId())
                .tipoCuenta(cuenta.getTipoCuenta())
                .numeroCuenta(cuenta.getNumeroCuenta())
                .saldo(cuenta.getSaldo())
                .activa(cuenta.getActiva())
                .build();
    }
}