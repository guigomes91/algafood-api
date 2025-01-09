package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
@Slf4j
public class FakeEmailService implements EnvioEmailService {
    @Override
    public EmailProperties.TipoEnvioEmail tipoDeEnvio() {
        return EmailProperties.TipoEnvioEmail.FAKE;
    }

    @Override
    public void enviar(Mensagem mensagem) {
        log.info("Envio de email fake para {}", mensagem.getDestinatarios());
    }
}
