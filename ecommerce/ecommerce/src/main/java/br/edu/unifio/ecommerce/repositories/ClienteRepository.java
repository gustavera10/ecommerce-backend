package br.edu.unifio.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unifio.ecommerce.entidades.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}