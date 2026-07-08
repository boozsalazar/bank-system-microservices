package com.bank.transacciones.entity;

import com.bank.transacciones.entity.enums.TipoTransaccion;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 12)
    private String numeroCuentaOrigen;

    @Column(length = 12)
    private String numeroCuentaDestino;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoTransaccion tipoTransaccion;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false)
    private LocalDateTime fecha;
}