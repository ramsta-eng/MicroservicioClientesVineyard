package com.vineyard.microservicios_cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.vineyard.microservicios_cliente.model.Cliente;
import com.vineyard.microservicios_cliente.repository.ClienteRepository;

import net.datafaker.Faker;
@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    private ClienteRepository clienteRepository;
    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        //Generar clientes
        for (int i = 0; i < 10; i++) {
            //Genera y rellena los datos
            Cliente cliente = new Cliente();
            cliente.setNombre(faker.name().firstName());
            cliente.setAppaterno(faker.name().lastName());
            cliente.setCorreo(faker.internet().emailAddress());
            cliente.setTelefono(String.valueOf(faker.number().numberBetween(100000000,999999999)));
            cliente.setDireccion(faker.address().streetAddress());

            //Guarda el cliente
            clienteRepository.save(cliente);
        }
        
    }
}
