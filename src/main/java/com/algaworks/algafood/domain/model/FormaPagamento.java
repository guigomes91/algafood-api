package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "forma_pagamento")
public class FormaPagamento {

    @EqualsAndHashCode.Include
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @UpdateTimestamp
    private OffsetDateTime dataAtualizacao;
}
