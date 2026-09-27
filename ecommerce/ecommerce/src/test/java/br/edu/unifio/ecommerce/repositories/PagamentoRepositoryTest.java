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
import br.edu.unifio.ecommerce.entidades.Pagamento;
import br.edu.unifio.ecommerce.entidades.Pedido;

@SpringBootTest
@Sql(scripts = "/limpar-banco.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PagamentoRepositoryTest {

    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void deveBuscarPagamentoPorId() {

        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");
        cliente.setTelefone("99999-9999");

        cliente = clienteRepository.save(cliente);

        Pedido pedido = new Pedido();
        pedido.setData(LocalDateTime.now());
        pedido.setStatus("PAGO");
        pedido.setValorTotal(new BigDecimal("1500.00"));
        pedido.setCliente(cliente);

        pedido = pedidoRepository.save(pedido);

        Pagamento pagamento = new Pagamento();
        pagamento.setValor(new BigDecimal("1500.00"));
        pagamento.setData(LocalDateTime.now());
        pagamento.setStatus("APROVADO");
        pagamento.setTipo("PIX");
        pagamento.setPedido(pedido);

        Pagamento salvo = repository.save(pagamento);

        Pagamento encontrado = repository.findById(salvo.getId()).orElse(null);

        assertNotNull(encontrado);
        assertEquals(new BigDecimal("1500.00"), encontrado.getValor());
        assertEquals("APROVADO", encontrado.getStatus());
        assertEquals("PIX", encontrado.getTipo());

        assertNotNull(encontrado.getPedido());
        assertEquals(pedido.getId(), encontrado.getPedido().getId());
    }

    @Test
    void deveListarPagamentos() {

        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");
        cliente.setTelefone("99999-9999");

        cliente = clienteRepository.save(cliente);

        Pedido pedido1 = new Pedido();
        pedido1.setData(LocalDateTime.now());
        pedido1.setStatus("PAGO");
        pedido1.setValorTotal(new BigDecimal("1500.00"));
        pedido1.setCliente(cliente);

        pedido1 = pedidoRepository.save(pedido1);

        Pedido pedido2 = new Pedido();
        pedido2.setData(LocalDateTime.now());
        pedido2.setStatus("PAGO");
        pedido2.setValorTotal(new BigDecimal("2000.00"));
        pedido2.setCliente(cliente);

        pedido2 = pedidoRepository.save(pedido2);

        Pagamento pagamento1 = new Pagamento();
        pagamento1.setValor(new BigDecimal("1500.00"));
        pagamento1.setData(LocalDateTime.now());
        pagamento1.setStatus("APROVADO");
        pagamento1.setTipo("PIX");
        pagamento1.setPedido(pedido1);

        Pagamento pagamento2 = new Pagamento();
        pagamento2.setValor(new BigDecimal("2000.00"));
        pagamento2.setData(LocalDateTime.now());
        pagamento2.setStatus("PROCESSANDO");
        pagamento2.setTipo("CARTAO");
        pagamento2.setPedido(pedido2);

        repository.save(pagamento1);
        repository.save(pagamento2);

        var pagamentos = repository.findAll();

        assertEquals(2, pagamentos.size());

        assertEquals(
            true,
            pagamentos.stream()
                .anyMatch(p -> p.getTipo().equals("PIX"))
        );

        assertEquals(
            true,
            pagamentos.stream()
                .anyMatch(p -> p.getTipo().equals("CARTAO"))
        );

        pagamentos.forEach(p -> {
            assertNotNull(p.getPedido());
        });
    }
}