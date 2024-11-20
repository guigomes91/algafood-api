package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.Restaurante;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public abstract class CozinhaMixin {

    @OneToMany(mappedBy = "cozinha")
    private List<Restaurante> restaurantes = new ArrayList<>();
}
