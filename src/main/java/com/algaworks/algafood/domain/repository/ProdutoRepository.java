package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Produto;

import java.util.Optional;

public interface ProdutoRepository extends CustomJpaRepository<Produto, Long> {

    Optional<Produto> findByIdAndRestauranteId(Long id, Long restauranteId);
}
