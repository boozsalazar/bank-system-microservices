package com.bank.cuentas.dto;

import com.bank.cuentas.entity.enums.TipoCuenta;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuentaRequestDTO {

    @NotNull(message="El cliente es obligatorio")
    private Long clienteId;

    @NotNull(message="El tipo de cuenta es obligatorio")
    private TipoCuenta tipoCuenta;
}
