package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Primary
@Service
@Slf4j
public class SandboxEmailService implements EnvioEmailService {

    private final SmtpEnvioEmailService smtpEnvioEmailService;
    private final EmailProperties emailProperties;

    @Override
    public void enviar(Mensagem mensagem) {
        mensagem.getDestinatarios().clear();
        mensagem.getDestinatarios().add(emailProperties.getSandbox());

        smtpEnvioEmailService.enviar(mensagem);
    }

    @Override
    public EmailProperties.TipoEnvioEmail tipoDeEnvio() {
        return EmailProperties.TipoEnvioEmail.SANDBOX;
    }

}
