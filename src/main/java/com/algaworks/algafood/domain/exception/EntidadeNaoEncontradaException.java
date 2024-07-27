package com.algaworks.algafood.domain.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
