package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class ItemPedido extends Id {

    @JoinColumn(name = "pedido_id", nullable = false)
    @ManyToOne
    private Pedido pedido;

    @JoinColumn(name = "produto_id", nullable = false)
    @ManyToOne
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private BigDecimal precoUnitario;

    private BigDecimal precoTotal;

    private String observacao;
}
