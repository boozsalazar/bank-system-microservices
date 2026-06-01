package com.bank.clientes.service;

import com.bank.clientes.dto.ClienteRequestDTO;
import com.bank.clientes.dto.ClienteResponseDTO;

import java.util.List;

public interface ClienteService {
    ClienteResponseDTO crearCliente(ClienteRequestDTO request);
    List<ClienteResponseDTO> listarClientes();
    ClienteResponseDTO obtenerPorId(Long id);
}
