package br.edu.unifio.ecommerce.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import br.edu.unifio.ecommerce.entidades.Categoria;
import br.edu.unifio.ecommerce.entidades.Cliente;
import br.edu.unifio.ecommerce.entidades.ItemPedido;
import br.edu.unifio.ecommerce.entidades.Pedido;
import br.edu.unifio.ecommerce.entidades.Produto;

@SpringBootTest
@Sql(scripts = "/limpar-banco.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ItemPedidoRepositoryTest {

    @Autowired
    private ItemPedidoRepository repository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    void deveBuscarItemPedidoPorId() {

        Categoria categoria = new Categoria();
        categoria.setNome("Eletrônicos");
        categoria.setDescricao("Produtos eletrônicos");

        categoria = categoriaRepository.save(categoria);

        Produto produto = new Produto();
        produto.setNome("Notebook");
        produto.setDescricao("Notebook");
        produto.setEstoque((short) 10);
        produto.setPreco(new BigDecimal("3500.00"));
        produto.setCategoria(categoria);

        produto = produtoRepository.save(produto);

        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");
        cliente.setTelefone("99999-9999");

        cliente = clienteRepository.save(cliente);

        Pedido pedido = new Pedido();
        pedido.setData(LocalDateTime.now());
        pedido.setStatus("PENDENTE");
        pedido.setValorTotal(new BigDecimal("7000.00"));
        pedido.setCliente(cliente);

        pedido = pedidoRepository.save(pedido);

        ItemPedido item = new ItemPedido();
        item.setQuantidade(2);
        item.setValorUnitario(new BigDecimal("3500.00"));
        item.setPedido(pedido);
        item.setProduto(produto);

        ItemPedido salvo = repository.save(item);

        ItemPedido encontrado = repository.findById(salvo.getId()).orElse(null);

        assertNotNull(encontrado);
        assertEquals(2, encontrado.getQuantidade());
        assertEquals(
            new BigDecimal("3500.00"),
            encontrado.getValorUnitario()
        );

        assertNotNull(encontrado.getPedido());
        assertEquals(
            pedido.getId(),
            encontrado.getPedido().getId()
        );

        assertNotNull(encontrado.getProduto());
        assertEquals(
            "Notebook",
            encontrado.getProduto().getNome()
        );
    }

    @Test
    void deveListarItensPedido() {

        Categoria categoria = new Categoria();
        categoria.setNome("Eletrônicos");
        categoria.setDescricao("Produtos eletrônicos");

        categoria = categoriaRepository.save(categoria);

        Produto produto = new Produto();
        produto.setNome("Notebook");
        produto.setDescricao("Notebook");
        produto.setEstoque((short) 10);
        produto.setPreco(new BigDecimal("3500.00"));
        produto.setCategoria(categoria);

        produto = produtoRepository.save(produto);

        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");
        cliente.setTelefone("99999-9999");

        cliente = clienteRepository.save(cliente);

        Pedido pedido = new Pedido();
        pedido.setData(LocalDateTime.now());
        pedido.setStatus("PENDENTE");
        pedido.setValorTotal(new BigDecimal("10500.00"));
        pedido.setCliente(cliente);

        pedido = pedidoRepository.save(pedido);

        ItemPedido item1 = new ItemPedido();
        item1.setQuantidade(2);
        item1.setValorUnitario(new BigDecimal("3500.00"));
        item1.setPedido(pedido);
        item1.setProduto(produto);

        ItemPedido item2 = new ItemPedido();
        item2.setQuantidade(1);
        item2.setValorUnitario(new BigDecimal("3500.00"));
        item2.setPedido(pedido);
        item2.setProduto(produto);

        repository.save(item1);
        repository.save(item2);

        var itens = repository.findAll();

        assertEquals(2, itens.size());

        itens.forEach(item -> {
            assertNotNull(item.getPedido());
            assertNotNull(item.getProduto());
            assertEquals(
                "Notebook",
                item.getProduto().getNome()
            );
        });
    }
}