package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.assembler.ProdutoModelDisassembler;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    private final CadastroRestauranteService cadastroRestauranteService;
    private final CadastroProdutoService cadastroProdutoService;
    private final ProdutoModelAssembler produtoModelAssembler;
    private final ProdutoModelDisassembler produtoModelDisassembler;

    @GetMapping
    public List<ProdutoModel> listar(@PathVariable Long restauranteId) {
        var restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        return produtoModelAssembler.toCollectionModel(restaurante.getProdutos());
    }

    @GetMapping("/{produtoId}")
    public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        var produto = cadastroProdutoService.buscarOuFalhar(produtoId, restauranteId);

        return produtoModelAssembler.toModel(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel adicionar(@PathVariable Long restauranteId,
                          @RequestBody @Valid ProdutoInput produtoInput) {
        var produto = produtoModelDisassembler.toDomainObject(produtoInput);
        var produtoNew = cadastroRestauranteService.adicionarProduto(restauranteId, produto);
        return produtoModelAssembler.toModel(produtoNew);
    }

    @PutMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel adicionar(@PathVariable Long restauranteId,
                                  @PathVariable Long produtoId,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        var produto = cadastroProdutoService.buscarOuFalhar(produtoId, restauranteId);
        produtoModelDisassembler.copyToDomainObject(produtoInput, produto);

        var produtoNew = cadastroRestauranteService.adicionarProduto(restauranteId, produto);
        return produtoModelAssembler.toModel(produtoNew);
    }
}
