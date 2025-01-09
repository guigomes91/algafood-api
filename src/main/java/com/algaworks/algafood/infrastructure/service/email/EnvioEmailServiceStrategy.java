package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class EnvioEmailServiceStrategy {

    private final List<EnvioEmailService> emailServices;

    public void executarEnvio(EmailProperties.TipoEnvioEmail tipoEnvio, EnvioEmailService.Mensagem mensagem) {
        Map<EmailProperties.TipoEnvioEmail, EnvioEmailService> executor = new HashMap<>();
        emailServices.forEach(envioEmailService -> executor.put(envioEmailService.tipoDeEnvio(), envioEmailService));

        final var envioEmailService = executor.get(tipoEnvio);
        envioEmailService.enviar(mensagem);
    }
}
