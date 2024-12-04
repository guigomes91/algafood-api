package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CadastroPedidoService {

    private final PedidoRepository pedidoRepository;
    private final CadastroRestauranteService cadastroRestauranteService;
    private final CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Transactional
    public Pedido salvar(Pedido pedido) {
        var restaurante = cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
        var formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());

        if (!restaurante.getFormasPagamento().contains(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento com código %s não permitido para o restaurante %s",
                    formaPagamento.getId(),
                    restaurante.getId()));
        }

        return pedidoRepository.save(pedido);
    }

    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(pedidoId));
    }
}
