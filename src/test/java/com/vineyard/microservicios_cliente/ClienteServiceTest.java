package com.vineyard.microservicios_cliente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.vineyard.microservicios_cliente.model.Cliente;
import com.vineyard.microservicios_cliente.repository.ClienteRepository;
import com.vineyard.microservicios_cliente.service.ClienteService;

@SpringBootTest
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @MockBean
    private ClienteRepository clienteRepository;
    
    @Test
    public void testFindAll(){
        // Definimos el comportamiento del mock dandole un cliente y pidiendole que devuelva una lista con este
        when(clienteRepository.findAll()).thenReturn(List.of(new Cliente(1,"ivan", "santander", "iv.santander@duocuc.cl", "56976532458", "Avenida skibidi toilet")));

        // Llama al metodo findAll del servicio.
        List<Cliente> clientes = clienteService.findAll();

        // Verificamos que la lista no sea nula y devuelva un solo cliente
        assertNotNull(clientes); // Verifica que la respuesta no sea nula
        assertEquals(1, clientes.size()); // Verifica que el tamaño de la lista sea de uno
    }

}
