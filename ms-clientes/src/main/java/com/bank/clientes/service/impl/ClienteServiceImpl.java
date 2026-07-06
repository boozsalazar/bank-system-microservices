package com.bank.clientes.service.impl;

import com.bank.clientes.dto.ClienteRequestDTO;
import com.bank.clientes.dto.ClienteResponseDTO;
import com.bank.clientes.entity.Cliente;
import com.bank.clientes.repository.ClienteRepository;
import com.bank.clientes.service.ClienteService;
import com.bank.clientes.validation.DocumentoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public ClienteResponseDTO crearCliente(ClienteRequestDTO request){
        if (!DocumentoValidator.esValido(
                request.getTipoDocumento(),
                request.getNumeroDocumento())){
            throw new IllegalArgumentException("Número de documento inválido para el tipo seleccionado");
        }

        if (clienteRepository.existsByNumeroDocumentoOrEmail(request.getNumeroDocumento(), request.getEmail())) {
            throw new IllegalArgumentException("El número de documento o el correo electrónico ya se encuentran registrados.");
        }

        Cliente cliente = Cliente.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .tipoDocumento(request.getTipoDocumento())
                .numeroDocumento(request.getNumeroDocumento())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .fechaNacimiento(request.getFechaNacimiento())
                .nacionalidad(request.getNacionalidad())
                .activo(true)
                .build();

        Cliente clienteGuardado = clienteRepository.save(cliente);

        return mapToResponse(clienteGuardado);
    }

    @Override
    public List<ClienteResponseDTO> listarClientes(){
        return clienteRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ClienteResponseDTO obtenerPorId(Long id){
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException ("Cliente no encontrado."));
        return mapToResponse(cliente);
    }

    public ClienteResponseDTO actualizarCliente(Long id, ClienteRequestDTO request){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException ("Cliente no encontrado."));
        if (!DocumentoValidator.esValido(request.getTipoDocumento(), request.getNumeroDocumento())){
            throw new IllegalArgumentException ("Número de documento inválido para el tipo seleccionado.");
        }

        if (clienteRepository.existeDuplicadoActualizacion(id, request.getNumeroDocumento(), request.getEmail())) {
            throw new IllegalArgumentException("El número de documento o el correo electrónico ya se encuentran registrados por otro cliente.");
        }

        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setTipoDocumento(request.getTipoDocumento());
        cliente.setNumeroDocumento(request.getNumeroDocumento());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setFechaNacimiento(request.getFechaNacimiento());
        cliente.setNacionalidad(request.getNacionalidad());

        Cliente actualizado = clienteRepository.save(cliente);

        return mapToResponse(actualizado);
    }

    public void desactivarCliente(Long id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException ("Cliente no encontrado."));

        cliente.setActivo(false);
        clienteRepository.save(cliente);
    }

    public ClienteResponseDTO mapToResponse(Cliente cliente){
        return ClienteResponseDTO.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .tipoDocumento(cliente.getTipoDocumento())
                .numeroDocumento(cliente.getNumeroDocumento())
                .email(cliente.getEmail())
                .telefono(cliente.getTelefono())
                .fechaNacimiento(cliente.getFechaNacimiento())
                .nacionalidad(cliente.getNacionalidad())
                .activo(cliente.getActivo())
                .build();
    }
}
