package com.bank.cuentas.entity;

import com.bank.cuentas.entity.enums.TipoCuenta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="cuentas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cuenta {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;

    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;

    @Column(unique = true)
    private String numeroCuenta;
    private BigDecimal saldo;
    private Boolean activa;

}
