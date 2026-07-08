package com.bank.cuentas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-clientes", path = "/api/clientes")
public interface ClienteFeignClient {

    @GetMapping("/{id}")
    Object obtenerPorId(@PathVariable("id") Long id);
}