package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Permissao extends Id {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;
}
