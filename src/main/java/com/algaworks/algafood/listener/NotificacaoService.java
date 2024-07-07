package com.algaworks.algafood.listener;

import com.algaworks.algafood.notificacao.NotificadorEmail;
import com.algaworks.algafood.service.ClienteAtivadoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoService {

    @Autowired
    private NotificadorEmail notificador;

    @EventListener
    private void clienteAtivadoListener(ClienteAtivadoEvent event) {
        notificador.notificar(event.getCliente(), "Seu cadastro no sistema está ativo!");
    }
}
