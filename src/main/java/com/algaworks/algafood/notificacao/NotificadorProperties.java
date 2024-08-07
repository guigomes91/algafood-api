package com.algaworks.algafood.notificacao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value = "notificador.email")
public class NotificadorProperties {

    /**
     * Host do servidor de e-mail
     */
    private String hostServidor;

    /**
     * Porta do servidor de e-mail
     */
    private Integer portaServidor = 25;

    public String getHostServidor() {
        return hostServidor;
    }

    public void setHostServidor(String hostServidor) {
        this.hostServidor = hostServidor;
    }

    public Integer getPortaServidor() {
        return portaServidor;
    }

    public void setPortaServidor(Integer portaServidor) {
        this.portaServidor = portaServidor;
    }
}
