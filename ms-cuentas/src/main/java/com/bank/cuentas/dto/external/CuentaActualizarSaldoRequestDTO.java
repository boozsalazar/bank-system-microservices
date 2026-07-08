package com.bank.cuentas.dto.external;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaActualizarSaldoRequestDTO {
    private String numeroCuenta;
    private BigDecimal nuevoSaldo;
}


