package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Pedido extends Id {

    @JoinColumn(name = "forma_pagamento_id", nullable = false)
    @ManyToOne
    private FormaPagamento formaPagamento;

    @JoinColumn(name = "restaurante_id", nullable = false)
    @ManyToOne
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens = new ArrayList<>();

    @Embedded
    private Endereco enderecoEntrega;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal taxaFrete;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    private LocalDateTime dataConfirmacao;

    private LocalDateTime dataCancelamento;

    private LocalDateTime dataEntrega;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPedido status;
}
