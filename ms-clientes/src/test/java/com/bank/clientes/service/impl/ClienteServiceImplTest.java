package com.bank.clientes.service.impl;

import com.bank.clientes.dto.ClienteRequestDTO;
import com.bank.clientes.dto.ClienteResponseDTO;
import com.bank.clientes.entity.Cliente;
import com.bank.clientes.entity.enums.TipoDocumento;
import com.bank.clientes.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private ClienteRequestDTO requestDTO;
    private Cliente clienteEntity;

    @BeforeEach
    void setUp() {
        requestDTO = new ClienteRequestDTO();
        requestDTO.setNombre("Mario");
        requestDTO.setApellido("Vargas");
        requestDTO.setTipoDocumento(TipoDocumento.DNI);
        requestDTO.setNumeroDocumento("45839201"); // 8 dígitos válidos
        requestDTO.setEmail("mario.vargas@bank.com");
        requestDTO.setTelefono("987654321");
        requestDTO.setFechaNacimiento(LocalDate.of(1990, 3, 28));
        requestDTO.setNacionalidad("Peruano");

        clienteEntity = Cliente.builder()
                .id(1L)
                .nombre("Mario")
                .apellido("Vargas")
                .tipoDocumento(TipoDocumento.DNI)
                .numeroDocumento("45839201")
                .email("mario.vargas@bank.com")
                .telefono("987654321")
                .fechaNacimiento(LocalDate.of(1990, 3, 28))
                .nacionalidad("Peruano")
                .activo(true)
                .build();
    }

    @Test
    @DisplayName("Debería registrar cliente con éxito si no existen duplicados")
    void crearCliente_Exitoso() {
        // Arrange
        when(clienteRepository.existsByNumeroDocumentoOrEmail(requestDTO.getNumeroDocumento(), requestDTO.getEmail()))
                .thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteEntity);

        // Act
        ClienteResponseDTO response = clienteService.crearCliente(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Mario", response.getNombre());
        assertTrue(response.getActivo());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Debería lanzar error al crear si el documento o correo ya existen")
    void crearCliente_Error_Duplicado() {
        // Arrange
        when(clienteRepository.existsByNumeroDocumentoOrEmail(requestDTO.getNumeroDocumento(), requestDTO.getEmail()))
                .thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.crearCliente(requestDTO);
        });

        assertEquals("El número de documento o el correo electrónico ya se encuentran registrados.", exception.getMessage());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Debería actualizar cliente con éxito si los datos nuevos son válidos")
    void actualizarCliente_Exitoso() {
        // Arrange
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteEntity));
        when(clienteRepository.existeDuplicadoActualizacion(1L, requestDTO.getNumeroDocumento(), requestDTO.getEmail()))
                .thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteEntity);

        // Act
        ClienteResponseDTO response = clienteService.actualizarCliente(1L, requestDTO);

        // Assert
        assertNotNull(response);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }
}