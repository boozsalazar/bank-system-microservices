package com.bank.clientes.dto;


import com.bank.clientes.entity.enums.TipoDocumento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClienteRequestDTO {

    @NotBlank(message="El nombre del cliente es obligatorio")
    private String nombre;

    @NotBlank
    private String apellido;

    @NotNull
    private TipoDocumento tipoDocumento;

    @NotBlank
    private String numeroDocumento;

    @NotBlank
    @Email(message="El correo electrónico no tiene un formato válido")
    private String email;

    @NotBlank
    private String telefono;

    @NotNull
    private LocalDate fechaNacimiento;

    @NotBlank
    private String nacionalidad;
}
