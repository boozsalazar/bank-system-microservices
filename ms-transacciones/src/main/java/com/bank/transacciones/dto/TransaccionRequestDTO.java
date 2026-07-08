package com.bank.transacciones.dto;

import com.bank.transacciones.entity.enums.TipoTransaccion;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransaccionRequestDTO {

    @NotNull(message = "El número de cuenta es obligatorio")
    private String numeroCuentaOrigen;

    private String numeroCuentaDestino;

    @NotNull(message = "El tipo de transacción es obligatorio")
    private TipoTransaccion tipoTransaccion;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a cero")
    private BigDecimal monto;
}