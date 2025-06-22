package com.vineyard.microservicios_cliente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vineyard.microservicios_cliente.model.Cliente;
import com.vineyard.microservicios_cliente.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag; 


@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Operaciones relacionadas con los clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    // Devuelve una lista con todos los clientes y si no devuelve un codigo 204 No Content
    @GetMapping
    @Operation(summary = "Obtener todos los clientes", description =  "Obtiene una lista con todos los clientes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")})
    public ResponseEntity<List<Cliente>> listar(){
        List<Cliente> clientes = clienteService.findAll();
        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }

    // Recibe un cliente, lo envia a la base de datos, devuelve una respuesta con codigo 201 Created y si no un codigo 400 Bad Request
    @PostMapping
    @Operation(summary = "Crear cliente", description =  "Ingresa datos sobre un cliente y lo crea")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")})
    public ResponseEntity<Cliente> guardar(@RequestBody Cliente cliente) {
        Cliente clienteNuevo = clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteNuevo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca clientes", description =  "Busca cliente segun su id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")})
    public ResponseEntity<Cliente> buscar(@PathVariable Integer id) {
        try {
            Cliente cliente = clienteService.findById(id);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Busca cliente por id y lo modifica en caso de no encontrarse no hace nada
    @PutMapping("/{id}")
    @Operation(summary = "Modificar cliente", description =  "Busca cliente por id y lo modifica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")})
    public ResponseEntity<Cliente> actualizar(@PathVariable Integer id, @RequestBody Cliente cliente) {
        try {
            Cliente cli = clienteService.findById(id);
            
            cli.setId(id);
            cli.setNombre(cliente.getNombre());
            cli.setAppaterno(cliente.getAppaterno());
            cli.setCorreo(cliente.getCorreo());
            cli.setTelefono(cliente.getTelefono());
            cli.setDireccion(cliente.getDireccion());  
            
            clienteService.save(cli);   
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Elimina entidad por id
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina cliente", description =  "Busca cliente por id y lo elimina")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")})
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try {
            clienteService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
