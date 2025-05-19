package com.vineyard.microservicios_cliente.repository;

import com.vineyard.microservicios_cliente.model.Cliente;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    // Busca a todos los clientes
    List<Cliente> findAll(Sort appaterno);

    // Busca por ID
    Cliente findById(Integer id);

}
