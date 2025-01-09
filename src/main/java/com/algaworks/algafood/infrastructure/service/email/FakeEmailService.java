package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Primary
@Service
@Slf4j
public class FakeEmailService extends SmtpEnvioEmailService {

    public FakeEmailService(JavaMailSender mailSender, EmailProperties emailProperties, Configuration freemarkerConfig) {
        super(mailSender, emailProperties, freemarkerConfig);
    }

    @Override
    public void enviar(Mensagem mensagem) {
        log.info("Envio de email fake para {}", mensagem.getDestinatarios());
    }
}
