package com.bank.cuentas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.bank.cuentas")
public class MsCuentasApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsCuentasApplication.class, args);
	}
}