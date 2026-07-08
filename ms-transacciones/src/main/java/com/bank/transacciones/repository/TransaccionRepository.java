package com.bank.transacciones.repository;

import com.bank.transacciones.entity.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    List<Transaccion> findByNumeroCuentaOrigenOrNumeroCuentaDestinoOrderByFechaDesc(String cuentaOrigen, String cuentaDestino);
}