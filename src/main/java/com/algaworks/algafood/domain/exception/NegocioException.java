package com.algaworks.algafood.domain.exception;

public class NegocioException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public NegocioException(String mensagem) {
        super(mensagem);
    }

    public NegocioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
