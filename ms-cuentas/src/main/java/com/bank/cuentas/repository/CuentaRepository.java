package com.bank.cuentas.repository;

import com.bank.cuentas.entity.Cuenta;
import com.bank.cuentas.entity.enums.TipoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    boolean existsByClienteIdAndTipoCuenta(Long clienteId, TipoCuenta tipoCuenta);
}
