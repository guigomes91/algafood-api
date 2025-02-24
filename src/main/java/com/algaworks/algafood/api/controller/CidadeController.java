package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.assembler.CidadeModelDisassembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private final CidadeRepository cidadeRepository;
    private final CadastroCidadeService cadastroCidadeService;
    private final CidadeModelAssembler cidadeModelAssembler;
    private final CidadeModelDisassembler cidadeModelDisassembler;

    @ApiOperation("Lista as cidades")
    @GetMapping
    public List<CidadeModel> listar() {
        return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
    }

    @ApiOperation("Busca uma cidade por ID")
    @GetMapping("/{id}")
    public CidadeModel buscar(
            @ApiParam(value = "ID de uma cidade", example = "1")
            @PathVariable Long id) {
        return cidadeModelAssembler.toModel(cadastroCidadeService.buscarOuFalhar(id));
    }

    @ApiOperation("Cadastra uma cidade")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel salvar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade")
            @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            final var cidade = cidadeModelDisassembler.toDomainObject(cidadeInput);
            return cidadeModelAssembler.toModel(cadastroCidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @ApiOperation("Atualiza uma cidade por ID")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CidadeModel alterar(
            @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
            @RequestBody @Valid CidadeInput cidadeInput,
            @ApiParam(value = "ID de uma cidade", example = "1")
            @PathVariable Long id) {
        try {
            Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(id);
            cidadeModelDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

            return cidadeModelAssembler
                    .toModel(cadastroCidadeService.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ApiOperation("Exclui uma cidade por ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(
            @ApiParam(value = "ID de uma cidade", example = "1")
            @PathVariable Long id) {
        cadastroCidadeService.remover(id);
    }
}
