package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1l;

    public PedidoNaoEncontradoException(String codigoPedido) {
        super(String.format("Não existe um cadastro de pedido com o código %s", codigoPedido));
    }
}
