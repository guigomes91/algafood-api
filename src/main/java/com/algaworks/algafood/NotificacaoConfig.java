package com.algaworks.algafood;

import com.algaworks.algafood.notificacao.NotificadorEmail;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoConfig {

    @Bean
    public NotificadorEmail notificadorEmail() {
        NotificadorEmail notificadorEmail = new NotificadorEmail("smtp.algamail.com.br");
        notificadorEmail.setCaixaAlta(true);

        return notificadorEmail;
    }
}
