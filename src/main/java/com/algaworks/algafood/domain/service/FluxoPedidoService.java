package com.algaworks.algafood.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FluxoPedidoService {

    private final EmissaoPedidoService emissaoPedidoService;
    private final EnvioEmailService envioEmailService;

    @Transactional
    public void confirmar(String codigoPedido) {
        var pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
        pedido.confirmar();

        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("O pedido de código <strong>" + pedido.getCodigo() + "</strong> foi confirmado")
                .destinatario(pedido.getCliente().getEmail())
                .build();
        envioEmailService.enviar(mensagem);
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
