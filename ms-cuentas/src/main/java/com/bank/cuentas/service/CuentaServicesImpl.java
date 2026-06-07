package com.bank.cuentas.service;

import com.bank.cuentas.dto.CuentaRequestDTO;
import com.bank.cuentas.dto.CuentaResponseDTO;
import com.bank.cuentas.entity.Cuenta;
import com.bank.cuentas.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaServicesImpl implements CuentaService{

    private static final String PREFIJO_CUENTA = "001-";
    private final CuentaRepository cuentaRepository;

    @Override
    public CuentaResponseDTO crearCuenta(CuentaRequestDTO request) {

        if(cuentaRepository.existsByClienteIdAndTipoCuenta(request.getClienteId(), request.getTipoCuenta())){
            throw new IllegalArgumentException("El cliente ya tiene una cuenta de tipo " + request.getTipoCuenta());
        }
        Cuenta cuenta = Cuenta.builder()
                .clienteId(request.getClienteId())
                .tipoCuenta(request.getTipoCuenta())
                .saldo(BigDecimal.ZERO)
                .activa(true)
                .build();

        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);
        cuentaGuardada.setNumeroCuenta(PREFIJO_CUENTA + String.format("%08d", cuentaGuardada.getId()));

        Cuenta cuentaActualizada = cuentaRepository.save(cuentaGuardada);
        return mapToResponse(cuentaActualizada);
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

    public CuentaResponseDTO mapToResponse(Cuenta cuenta){
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