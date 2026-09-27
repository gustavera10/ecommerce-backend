package br.edu.unifio.ecommerce.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import br.edu.unifio.ecommerce.entidades.Cliente;

@SpringBootTest
@Sql(scripts = "/limpar-banco.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository repository;

    @Test
    void deveBuscarClientePorId() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");
        cliente.setTelefone("99999-9999");

        Cliente salvo = repository.save(cliente);

        Cliente encontrado = repository.findById(salvo.getId()).orElse(null);

        assertNotNull(encontrado);
        assertEquals("João Silva", encontrado.getNome());
        assertEquals("joao@email.com", encontrado.getEmail());
        assertEquals("99999-9999", encontrado.getTelefone());
    }

    @Test
    void deveListarClientes() {
        Cliente cliente1 = new Cliente();
        cliente1.setNome("João Silva");
        cliente1.setEmail("joao@email.com");
        cliente1.setTelefone("99999-9999");

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Maria Souza");
        cliente2.setEmail("maria@email.com");
        cliente2.setTelefone("88888-8888");

        repository.save(cliente1);
        repository.save(cliente2);

        var clientes = repository.findAll();

        assertEquals(2, clientes.size());
        assertEquals(true, clientes.stream()
                .anyMatch(c -> c.getNome().equals("João Silva")));
        assertEquals(true, clientes.stream()
                .anyMatch(c -> c.getNome().equals("Maria Souza")));
    }
}