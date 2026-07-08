package com.bank.cuentas.repository;

import com.bank.cuentas.entity.Cuenta;
import com.bank.cuentas.entity.enums.TipoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    boolean existsByClienteIdAndTipoCuenta(Long clienteId, TipoCuenta tipoCuenta);
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
}
