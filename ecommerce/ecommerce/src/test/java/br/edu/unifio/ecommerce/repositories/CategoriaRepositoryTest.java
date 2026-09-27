package br.edu.unifio.ecommerce.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.unifio.ecommerce.entidades.Categoria;

@SpringBootTest
public class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository repository;

    @BeforeEach
    void limparBanco() {
        repository.deleteAll();
    }

    @Test
    void deveBuscarCategoriaPorId() {
        Categoria categoria = new Categoria();
        categoria.setNome("Eletrônicos");
        categoria.setDescricao("Produtos eletrônicos");

        Categoria salva = repository.save(categoria);

        Categoria encontrada = repository.findById(salva.getId()).orElse(null);

        assertTrue(encontrada != null);
        assertEquals("Eletrônicos", encontrada.getNome());
        assertEquals("Produtos eletrônicos", encontrada.getDescricao());
    }

    @Test
    void deveListarCategorias() {
        Categoria categoria1 = new Categoria();
        categoria1.setNome("Eletrônicos");
        categoria1.setDescricao("Produtos eletrônicos");

        Categoria categoria2 = new Categoria();
        categoria2.setNome("Roupas");
        categoria2.setDescricao("Roupas em geral");

        repository.save(categoria1);
        repository.save(categoria2);

        var categorias = repository.findAll();

        assertEquals(2, categorias.size());
        assertEquals("Eletrônicos", categorias.get(0).getNome());
        assertEquals("Roupas", categorias.get(1).getNome());
    }
}