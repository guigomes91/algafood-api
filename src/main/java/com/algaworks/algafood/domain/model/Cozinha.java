package com.algaworks.algafood.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

@JsonRootName(value = "gastronomia")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@Entity
public class Cozinha extends Id {

    //@JsonIgnore
    @JsonProperty(value = "titulo")
    @Column(length = 30, nullable = false)
    private String nome;
}
