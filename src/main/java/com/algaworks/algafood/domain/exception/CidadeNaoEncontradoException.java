package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1l;

    public CidadeNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public CidadeNaoEncontradoException(Long estadoId) {
        this(String.format("Não existe um cadastro de cidade com o código %d", estadoId));
    }
}
