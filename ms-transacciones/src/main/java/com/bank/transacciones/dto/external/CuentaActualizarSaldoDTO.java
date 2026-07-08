package com.bank.transacciones.dto.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaActualizarSaldoDTO {
    private String numeroCuenta;
    private BigDecimal nuevoSaldo;
}