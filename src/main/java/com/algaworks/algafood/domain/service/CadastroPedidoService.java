package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CadastroPedidoService {

    private final PedidoRepository pedidoRepository;

    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(pedidoId));
    }
}
