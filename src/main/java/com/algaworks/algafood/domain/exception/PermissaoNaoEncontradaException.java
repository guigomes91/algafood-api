package com.algaworks.algafood.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1l;

    public PermissaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public PermissaoNaoEncontradaException(Long permissaoId) {
        this(String.format("Não existe um cadastro de permissão com o código %d", permissaoId));
    }
}
