package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@MappedSuperclass
public class Id {

    @EqualsAndHashCode.Include
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Valid
    private Long id;
}
