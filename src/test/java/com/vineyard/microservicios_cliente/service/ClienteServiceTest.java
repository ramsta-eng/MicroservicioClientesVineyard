package com.vineyard.microservicios_cliente.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

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

    @Test
    public void testFindById() {
        // Cree dos variable porque findById pide tipo dato long y mi cliente pide un Integer
        long codigo = 1;
        Integer codigo2= 1;
        Cliente cliente = new Cliente(codigo2, "pepe", "tapia", "ptapia@gmail.com", "8349056", "Avenida hola 777");

        // Define el comportamiento del mock cuando se llame a findById() con "1", devuelve una Cliente.
        when(clienteRepository.findById(codigo)).thenReturn(Optional.of(cliente));

        Cliente found = clienteService.findById(codigo);
        assertNotNull(found);
        assertEquals(codigo2, found.getId());
    }

    @Test
    public void testSave() {
        Cliente cliente = new Cliente(1, "pepe", "tapia", "ptapia@gmail.com", "8349056", "Avenida hola 777");

        // Define el comportamiento del mock cuando se llame a save(), devuelve el Cliente oporcionado
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        //Llamamos al metodo save() del servicio
        Cliente saved = clienteService.save(cliente);

        //Verificamos que el cliente guardado no sea nulo y que su nombre coincida con el esperado
        assertNotNull(saved);
        assertEquals("pepe", saved.getNombre());
    }
    @Test
    public void testDeleteById() {
        long codigo = 1;

        // Define el comportamiento del mock: cuando se llame a deleteById(), no hace nada.
        doNothing().when(clienteRepository).deleteById(codigo);

        // Llama al método deleteByCodigo() del servicio.
        clienteService.delete(codigo);

        // Verifica que el método deleteById() del repositorio se haya llamado exactamente una vez con el código proporcionado.
        verify(clienteRepository, times(1)).deleteById(codigo);
    }
}
