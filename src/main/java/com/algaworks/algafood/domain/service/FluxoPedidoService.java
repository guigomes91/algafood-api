package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FluxoPedidoService {

    private final EmissaoPedidoService emissaoPedidoService;
    private final PedidoRepository pedidoRepository;

    @Transactional
    public void confirmar(String codigoPedido) {
        var pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
        pedido.confirmar();

        pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelar(String codigoPedido) {
        var pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
        pedido.cancelar();
    }

    @Transactional
    public void entregar(String codigoPedido) {
        var pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
        pedido.entregar();
    }
}
