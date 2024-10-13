package com.algaworks.algafood.domain.exception;

public class EntidadeEmUsoException extends NegocioException {

    private static final long serialVersionUID = 1l;

    public EntidadeEmUsoException(String mensagem) {
        super(mensagem);
    }
}
