package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EmissaoPedidoService {

    private final PedidoRepository pedidoRepository;
    private final CadastroRestauranteService cadastroRestauranteService;
    private final CadastroFormaPagamentoService cadastroFormaPagamentoService;
    private final CadastroProdutoService cadastroProdutoService;
    private final CadastroCidadeService cadastroCidadeService;
    private final CadastroUsuarioService cadastroUsuarioService;

    @Transactional
    public Pedido salvar(Pedido pedido) {
        var cidade = cadastroCidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
        var cliente = cadastroUsuarioService.buscarOuFalhar(pedido.getCliente().getId());
        var restaurante = cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
        var formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());
        verificaFormaPagamentoPertenceAoRestaurante(restaurante, formaPagamento);

        pedido.getItens()
                        .forEach(itemPedido -> {
                            var produto = cadastroProdutoService.buscarOuFalhar(itemPedido.getProduto().getId(),
                                    restaurante.getId());
                            itemPedido.setPedido(pedido);
                            itemPedido.setProduto(produto);
                            itemPedido.calcularPrecoTotal();
                        });
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setCliente(cliente);
        pedido.calcularValorTotal();
        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);

        return pedidoRepository.save(pedido);
    }

    private static void verificaFormaPagamentoPertenceAoRestaurante(Restaurante restaurante, FormaPagamento formaPagamento) {
        if (!restaurante.getFormasPagamento().contains(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento com código %s não permitido para o restaurante %s",
                    formaPagamento.getId(),
                    restaurante.getId()));
        }
    }

    public Pedido buscarOuFalhar(String codigoPedido) {
        return pedidoRepository.findByCodigo(codigoPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
    }
}
