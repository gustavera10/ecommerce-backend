package br.edu.unifio.ecommerce.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import br.edu.unifio.ecommerce.entidades.Categoria;
import br.edu.unifio.ecommerce.entidades.Produto;

@SpringBootTest
@Sql(scripts = "/limpar-banco.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    void deveBuscarProdutoPorId() {
        Categoria categoria = new Categoria();
        categoria.setNome("Eletrônicos");
        categoria.setDescricao("Produtos eletrônicos");
        categoria = categoriaRepository.save(categoria);

        Produto produto = new Produto();
        produto.setNome("Notebook");
        produto.setDescricao("Notebook 15 polegadas");
        produto.setEstoque((short) 10);
        produto.setPreco(new BigDecimal("3500.00"));
        produto.setCategoria(categoria);

        Produto salvo = repository.save(produto);

        Produto encontrado = repository.findById(salvo.getId()).orElse(null);

        assertNotNull(encontrado);
        assertEquals("Notebook", encontrado.getNome());
        assertEquals("Notebook 15 polegadas", encontrado.getDescricao());
        assertEquals((short) 10, encontrado.getEstoque());
        assertEquals(new BigDecimal("3500.00"), encontrado.getPreco());
        assertNotNull(encontrado.getCategoria());
        assertEquals("Eletrônicos", encontrado.getCategoria().getNome());
    }

    @Test
    void deveListarProdutos() {
        Categoria categoria = new Categoria();
        categoria.setNome("Eletrônicos");
        categoria.setDescricao("Produtos eletrônicos");
        categoria = categoriaRepository.save(categoria);

        Produto produto1 = new Produto();
        produto1.setNome("Notebook");
        produto1.setDescricao("Notebook");
        produto1.setEstoque((short) 10);
        produto1.setPreco(new BigDecimal("3500.00"));
        produto1.setCategoria(categoria);

        Produto produto2 = new Produto();
        produto2.setNome("Celular");
        produto2.setDescricao("Celular");
        produto2.setEstoque((short) 20);
        produto2.setPreco(new BigDecimal("2000.00"));
        produto2.setCategoria(categoria);

        repository.save(produto1);
        repository.save(produto2);

        var produtos = repository.findAll();

        assertEquals(2, produtos.size());
        assertEquals(true, produtos.stream()
                .anyMatch(p -> p.getNome().equals("Notebook")));
        assertEquals(true, produtos.stream()
                .anyMatch(p -> p.getNome().equals("Celular")));

        produtos.forEach(p -> {
            assertNotNull(p.getCategoria());
            assertEquals("Eletrônicos", p.getCategoria().getNome());
        });
    }
}