package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.infrastructure.service.email.FakeEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FluxoPedidoService {

    private final EmissaoPedidoService emissaoPedidoService;
    private final EnvioEmailService envioEmailService;
    private final FakeEmailService fakeEmailService;
    private final EmailProperties emailProperties;

    @Transactional
    public void confirmar(String codigoPedido) {
        var pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
        pedido.confirmar();

        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        if (EmailProperties.TipoEnvioEmail.SMTP == emailProperties.getImpl()) {
            envioEmailService.enviar(mensagem);
        } else {
            fakeEmailService.enviar(mensagem);
        }

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
