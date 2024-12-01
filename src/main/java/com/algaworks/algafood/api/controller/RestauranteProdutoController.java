package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    private final CadastroRestauranteService cadastroRestauranteService;
    private final ProdutoModelAssembler produtoModelAssembler;

    @GetMapping
    public List<ProdutoModel> listar(@PathVariable Long restauranteId) {
        var restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        return produtoModelAssembler.toCollectionModel(restaurante.getProdutos());
    }

    @PutMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adicionar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        cadastroRestauranteService.adicionarProduto(restauranteId, produtoId);
    }
}
