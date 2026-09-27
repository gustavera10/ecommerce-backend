package br.edu.unifio.ecommerce.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import br.edu.unifio.ecommerce.entidades.Cliente;
import br.edu.unifio.ecommerce.entidades.Pedido;

@SpringBootTest
@Sql(scripts = "/limpar-banco.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void deveBuscarPedidoPorId() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");
        cliente.setTelefone("99999-9999");

        cliente = clienteRepository.save(cliente);

        Pedido pedido = new Pedido();
        pedido.setData(LocalDateTime.now());
        pedido.setStatus("PENDENTE");
        pedido.setValorTotal(new BigDecimal("1500.00"));
        pedido.setCliente(cliente);

        Pedido salvo = repository.save(pedido);

        Pedido encontrado = repository.findById(salvo.getId()).orElse(null);

        assertNotNull(encontrado);
        assertEquals("PENDENTE", encontrado.getStatus());
        assertEquals(new BigDecimal("1500.00"), encontrado.getValorTotal());
        assertNotNull(encontrado.getCliente());
        assertEquals("João Silva", encontrado.getCliente().getNome());
    }

    @Test
    void deveListarPedidos() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");
        cliente.setTelefone("99999-9999");

        cliente = clienteRepository.save(cliente);

        Pedido pedido1 = new Pedido();
        pedido1.setData(LocalDateTime.now());
        pedido1.setStatus("PENDENTE");
        pedido1.setValorTotal(new BigDecimal("1500.00"));
        pedido1.setCliente(cliente);

        Pedido pedido2 = new Pedido();
        pedido2.setData(LocalDateTime.now());
        pedido2.setStatus("PAGO");
        pedido2.setValorTotal(new BigDecimal("2500.00"));
        pedido2.setCliente(cliente);

        repository.save(pedido1);
        repository.save(pedido2);

        var pedidos = repository.findAll();

        assertEquals(2, pedidos.size());

        assertEquals(
            true,
            pedidos.stream()
                .anyMatch(p -> p.getStatus().equals("PENDENTE"))
        );

        assertEquals(
            true,
            pedidos.stream()
                .anyMatch(p -> p.getStatus().equals("PAGO"))
        );

        pedidos.forEach(p -> {
            assertNotNull(p.getCliente());
            assertEquals("João Silva", p.getCliente().getNome());
        });
    }
}