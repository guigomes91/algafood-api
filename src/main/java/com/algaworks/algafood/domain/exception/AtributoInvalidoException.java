package com.algaworks.algafood.domain.exception;

public class AtributoInvalidoException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public AtributoInvalidoException(String mensagem) {
        super(mensagem);
    }
}

