package com.bank.transacciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // 🔥 Importación obligatoria

@SpringBootApplication
@EnableFeignClients
public class MsTransaccionesApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsTransaccionesApplication.class, args);
    }
}