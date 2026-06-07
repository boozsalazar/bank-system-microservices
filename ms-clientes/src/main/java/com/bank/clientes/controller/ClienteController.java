package com.bank.clientes.controller;

import com.bank.clientes.dto.ClienteRequestDTO;
import com.bank.clientes.dto.ClienteResponseDTO;
import com.bank.clientes.service.impl.ClienteServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteServiceImpl clienteService;

    @PostMapping
    public ClienteResponseDTO crearCliente(
             @Valid @RequestBody ClienteRequestDTO request
    ){
        return clienteService.crearCliente(request);
    }

    @GetMapping
    public List<ClienteResponseDTO> listarClientes(){
        return clienteService.listarClientes();
    }

    @GetMapping("/{id}")
    public ClienteResponseDTO obtenerPorId(
            @PathVariable Long id){
        return clienteService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public ClienteResponseDTO actualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO request){
        return clienteService.actualizarCliente(id, request);
    }

    @DeleteMapping("/{id}")
    public void desactivarCliente(
            @PathVariable Long id
    ){
        clienteService.desactivarCliente(id);
    }

}
