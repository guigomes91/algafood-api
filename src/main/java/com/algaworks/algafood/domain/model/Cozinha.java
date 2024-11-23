package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Cozinha {

    @javax.persistence.Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Groups.CozinhaId.class)
    private Long id;

    @NotBlank
    @Column(length = 30, nullable = false)
    private String nome;

    @OneToMany(mappedBy = "cozinha")
    @JsonBackReference
    private List<Restaurante> restaurantes = new ArrayList<>();
}
