package com.vineyard.microservicios_cliente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vineyard.microservicios_cliente.model.Cliente;
import com.vineyard.microservicios_cliente.repository.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(long id) {
        return clienteRepository.findById(id).get();
    }

    // Actualiza o crea si no existe
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void delete(long id) {
        clienteRepository.deleteById(id);
    }
}
