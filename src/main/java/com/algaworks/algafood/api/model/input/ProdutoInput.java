package com.algaworks.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoInput {

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @Positive
    @NotNull
    private BigDecimal preco;

    @NotNull
    private boolean ativo;
}
