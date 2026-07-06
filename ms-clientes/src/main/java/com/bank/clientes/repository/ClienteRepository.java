package com.bank.clientes.repository;

import com.bank.clientes.dto.ClienteResponseDTO;
import com.bank.clientes.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByNumeroDocumentoOrEmail(String numeroDocumento, String email);

    @Query("SELECT COUNT(c) > 0 FROM Cliente c WHERE c.id != :id AND (c.numeroDocumento = :doc OR c.email = :email)")
    boolean existeDuplicadoActualizacion(@Param("id") Long id, @Param("doc") String numeroDocumento, @Param("email") String email);
}