package com.bank.clientes.entity;

import com.bank.clientes.entity.enums.TipoDocumento;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="clientes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable=false, length=50)
    private String nombre;

    @NotBlank
    @Column(nullable=false, length=50)
    private String apellido;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private TipoDocumento tipoDocumento;

    @NotBlank
    @Column(nullable=false, unique=true)
    private String numeroDocumento;

    @NotBlank
    @Email
    @Column(nullable=false, unique=true, length=50)
    private String email;

    @NotBlank
    @Column(nullable=false, length=9)
    private String telefono;

    @NotNull
    @Column(nullable=false)
    private LocalDate fechaNacimiento;

    @NotBlank
    @Column(nullable=false)
    private String nacionalidad;

    @NotNull
    private Boolean activo;
}