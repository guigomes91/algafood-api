package com.algaworks.algafood.domain.exception;

public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1l;

    public FotoProdutoNaoEncontradoException(String produtoId) {
        super(String.format("Não existe um cadastro de foto para o produto de código %s", produtoId));
    }
}
