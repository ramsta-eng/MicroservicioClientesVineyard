package com.vineyard.microservicios_cliente.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vineyard.microservicios_cliente.model.Cliente;
import com.vineyard.microservicios_cliente.service.ClienteService;

@WebMvcTest(ClienteController.class) //Indocamos que se esta probando el contolador de Cliente
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc; // Nos permite crear peticiones HTTP

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper; // Lo utilizamos para convertir objetos Java <--> Json

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        // Configura un objeto cliente de ejemplo antes de cada prueba
        cliente = new Cliente();
        cliente.setId(1);
        cliente.setNombre("Pepe");
        cliente.setAppaterno("Grillo");
        cliente.setCorreo("pgrillo@gmail.com");
        cliente.setDireccion("Avenida tilin sech 29");
        cliente.setTelefono("56987877878");
    }

    @Test
    public void testListar() throws Exception {
        // Definimos que el mock cuando llame a find all devuelva una lista con el cliente de prueba
        when(clienteService.findAll()).thenReturn(List.of(cliente));

        // Realiza una peticion GET a /api/v1/clientes y verifica que la respuesta sea correcta
        mockMvc.perform(get("/api/v1/clientes"))
                .andExpect(status().isOk())// Verifica que el estado de respuesta sea 200 Ok
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Pepe"))
                .andExpect(jsonPath("$[0].appaterno").value("Grillo"))
                .andExpect(jsonPath("$[0].correo").value("pgrillo@gmail.com"))
                .andExpect(jsonPath("$[0].direccion").value("Avenida tilin sech 29"))
                .andExpect(jsonPath("$[0].telefono").value("56987877878"));

    }

    @Test
    public void testBuscar() throws Exception {
        when(clienteService.findById(1)).thenReturn(cliente);

        mockMvc.perform(get("/api/v1/clientes/1"))
                .andExpect(status().isOk())// Verifica que el estado de respuesta sea 200 Ok
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Pepe"))
                .andExpect(jsonPath("$.appaterno").value("Grillo"))
                .andExpect(jsonPath("$.correo").value("pgrillo@gmail.com"))
                .andExpect(jsonPath("$.direccion").value("Avenida tilin sech 29"))
                .andExpect(jsonPath("$.telefono").value("56987877878"));        
    }

    @Test
    public void testGuardar() throws Exception {
        // Define el comportamiento del mock: cuando se llame a save(), devuelve el objeto Cliente
        when(clienteService.save(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated())// Verifica que el estado de respuesta sea 201 Ok
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Pepe"))
                .andExpect(jsonPath("$.appaterno").value("Grillo"))
                .andExpect(jsonPath("$.correo").value("pgrillo@gmail.com"))
                .andExpect(jsonPath("$.direccion").value("Avenida tilin sech 29"))
                .andExpect(jsonPath("$.telefono").value("56987877878")); 
    }

    @Test
    public void testActualizar() throws Exception {
        // Define el comportamiento del mock: cuando se llame a save(), devuelve el objeto cliente
        when(clienteService.save(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(put("/api/v1/clientes/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(cliente))) // Convierte el objeto Estudiante a JSON        
                .andExpect(status().isOk())// Verifica que el estado de respuesta sea 200 Ok
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Pepe"))
                .andExpect(jsonPath("$.appaterno").value("Grillo"))
                .andExpect(jsonPath("$.correo").value("pgrillo@gmail.com"))
                .andExpect(jsonPath("$.direccion").value("Avenida tilin sech 29"))
                .andExpect(jsonPath("$.telefono").value("56987877878"));  
    }

    @Test
    public void testEliminar() throws Exception {

        doNothing().when(clienteService).delete(1);
        
        mockMvc.perform(delete("/api/estudiantes/1"))
                .andExpect(status().isOk());
        verify(clienteService, times(1)).delete(1);
    }
}
