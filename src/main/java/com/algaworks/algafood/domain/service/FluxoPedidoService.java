package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.infrastructure.service.email.EnvioEmailServiceStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FluxoPedidoService {

    private final EmissaoPedidoService emissaoPedidoService;
    private final EmailProperties emailProperties;
    private final EnvioEmailServiceStrategy envioEmailServiceStrategy;
    private final PedidoRepository pedidoRepository;

    @Transactional
    public void confirmar(String codigoPedido) {
        var pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
        pedido.confirmar();

        pedidoRepository.save(pedido);

        /*var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmailServiceStrategy.executarEnvio(emailProperties.getImpl(), mensagem);*/
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
