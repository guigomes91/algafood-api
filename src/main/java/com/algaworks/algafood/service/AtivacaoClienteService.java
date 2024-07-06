package com.algaworks.algafood.service;

import com.algaworks.algafood.modelo.Cliente;
import com.algaworks.algafood.notificacao.ATipoNotificador;
import com.algaworks.algafood.notificacao.Notificador;
import com.algaworks.algafood.notificacao.TipoNotificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//@Component
public class AtivacaoClienteService {

    @ATipoNotificador(TipoNotificador.URGENTE)
    @Autowired
    private Notificador notificador;

    @PostConstruct
    public void init() {
        System.out.println("INIT");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("DESTROY");
    }

    public void ativar(Cliente cliente) {
        cliente.ativar();
        notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
    }
}
