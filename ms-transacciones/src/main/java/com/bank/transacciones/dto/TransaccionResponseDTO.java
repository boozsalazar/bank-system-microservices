package com.bank.transacciones.dto;

import com.bank.transacciones.entity.enums.TipoTransaccion;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransaccionResponseDTO {
    private Long id;
    private String numeroCuentaOrigen;
    private String numeroCuentaDestino;
    private TipoTransaccion tipoTransaccion;
    private BigDecimal monto;
    private LocalDateTime fecha;
}