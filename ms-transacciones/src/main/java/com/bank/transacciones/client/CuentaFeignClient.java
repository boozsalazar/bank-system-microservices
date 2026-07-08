package com.bank.transacciones.client;

import com.bank.transacciones.dto.external.CuentaActualizarSaldoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "ms-cuentas", path = "/api/cuentas")
public interface CuentaFeignClient {

    @GetMapping("/buscar")
    Map<String, Object> obtenerCuentaPorNumero(@RequestParam("numeroCuenta") String numeroCuenta);

    @PutMapping("/actualizar-saldo")
    void actualizarSaldo(@RequestBody CuentaActualizarSaldoDTO actualizarSaldoDTO);
}