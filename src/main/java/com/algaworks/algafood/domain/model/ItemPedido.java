package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class ItemPedido {

    @EqualsAndHashCode.Include
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public void calcularPrecoTotal() {
        this.precoUnitario = produto.getPreco();
        this.precoTotal = produto.getPreco().multiply(BigDecimal.valueOf(this.quantidade));
    }
}
