package com.bank.cuentas.dto;

import com.bank.cuentas.entity.enums.TipoCuenta;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CuentaResponseDTO {

    private Long id;
    private Long clienteId;
    private TipoCuenta tipoCuenta;
    private String numeroCuenta;
    private BigDecimal saldo;
    private Boolean activa;
}
