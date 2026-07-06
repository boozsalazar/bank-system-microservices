package com.bank.clientes.dto;

import com.bank.clientes.entity.enums.TipoDocumento;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ClienteResponseDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private Boolean activo;
}
