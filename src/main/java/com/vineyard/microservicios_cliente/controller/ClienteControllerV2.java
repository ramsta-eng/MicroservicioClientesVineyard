package com.vineyard.microservicios_cliente.controller;

import com.vineyard.microservicios_cliente.assemblers.ClienteModelAssembler;
import com.vineyard.microservicios_cliente.model.Cliente;
import com.vineyard.microservicios_cliente.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/clientes")
@Tag(name = "Clientes V2", description = "Operaciones relacionadas con los clientes implementando HATEOAS")
public class ClienteControllerV2 {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteModelAssembler assembler;

    // GET todos los clientes
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los clientes", description =  "Obtiene una lista con todos los clientes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")})
    public ResponseEntity<CollectionModel<EntityModel<Cliente>>> listar() {
        List<Cliente> clientes = clienteService.findAll();

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Cliente>> clientesModel = clientes.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(clientesModel,
                        linkTo(methodOn(ClienteControllerV2.class).listar()).withSelfRel())
        );
    }
    
    // GET cliente por ID
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Busca clientes", description =  "Busca cliente segun su id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")})
    public ResponseEntity<EntityModel<Cliente>> buscar(@PathVariable Integer id) {
        try {
            Cliente cliente = clienteService.findById(id);
            return ResponseEntity.ok(assembler.toModel(cliente));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST nuevo cliente
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear cliente", description =  "Ingresa datos sobre un cliente y lo crea")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")})
    public ResponseEntity<EntityModel<Cliente>> guardar(@RequestBody Cliente cliente) {
        Cliente clienteNuevo = clienteService.save(cliente);
        EntityModel<Cliente> model = assembler.toModel(clienteNuevo);

        return ResponseEntity
                .created(linkTo(methodOn(ClienteControllerV2.class).buscar(clienteNuevo.getId())).toUri())
                .body(model);
    }

    // PUT actualizar cliente
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Modificar cliente", description =  "Busca cliente por id y lo modifica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")})
    public ResponseEntity<EntityModel<Cliente>> actualizar(@PathVariable Integer id, @RequestBody Cliente cliente) {
        try {
            Cliente cli = clienteService.findById(id);

            cli.setNombre(cliente.getNombre());
            cli.setAppaterno(cliente.getAppaterno());
            cli.setCorreo(cliente.getCorreo());
            cli.setTelefono(cliente.getTelefono());
            cli.setDireccion(cliente.getDireccion());

            Cliente actualizado = clienteService.save(cli);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE cliente
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Elimina cliente", description =  "Busca cliente por id y lo elimina")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")})
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            clienteService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

