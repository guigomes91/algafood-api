package com.algaworks.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PedidoInput {

    @Valid
    @NotNull
    private RestauranteIdInput restaurante;

    @NotNull
    @Valid
    private FormaPagamentoIdInput formaPagamento;

    @Valid
    @NotNull
    private EnderecoInput enderecoEntrega;

    @Valid
    @NotNull
    @Size(min = 1)
    List<ItemPedidoInput> itens;
}
