package com.algaworks.algafood.listener;

import com.algaworks.algafood.notificacao.ATipoNotificador;
import com.algaworks.algafood.notificacao.Notificador;
import com.algaworks.algafood.notificacao.TipoNotificador;
import com.algaworks.algafood.service.ClienteAtivadoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoService {

    @ATipoNotificador(TipoNotificador.SEM_URGENCIA)
    @Autowired
    private Notificador notificador;

    @EventListener
    private void clienteAtivadoListener(ClienteAtivadoEvent event) {
        notificador.notificar(event.getCliente(), "Seu cadastro no sistema está ativo!");
    }
}
