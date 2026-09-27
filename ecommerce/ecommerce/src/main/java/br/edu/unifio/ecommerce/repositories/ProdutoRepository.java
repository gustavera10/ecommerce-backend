package br.edu.unifio.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unifio.ecommerce.entidades.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}