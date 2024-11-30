package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.assembler.GrupoModelDisassembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/grupos")
public class GrupoController {

    private final CadastroGrupoService cadastroGrupoService;
    private final GrupoRepository grupoRepository;
    private final GrupoModelAssembler grupoModelAssembler;
    private final GrupoModelDisassembler grupoModelDisassembler;

    @GetMapping
    public List<GrupoModel> listar() {
        return grupoModelAssembler.toCollectionModel(grupoRepository.findAll());
    }

    @GetMapping("/{id}")
    public GrupoModel buscar(@PathVariable Long id) {
        return grupoModelAssembler.toModel(cadastroGrupoService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel salvar(@RequestBody @Valid GrupoInput grupoInput) {
        try {
            final var grupo = grupoModelDisassembler.toDomainObject(grupoInput);
            return grupoModelAssembler.toModel(cadastroGrupoService.salvar(grupo));
        } catch (GrupoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public GrupoModel alterar(@RequestBody @Valid GrupoInput grupoInput, @PathVariable Long id) {
        try {
            Grupo grupoAtual = cadastroGrupoService.buscarOuFalhar(id);
            grupoModelDisassembler.copyToDomainObject(grupoInput, grupoAtual);
            return grupoModelAssembler.toModel(cadastroGrupoService.salvar(grupoAtual));
        } catch (GrupoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroGrupoService.remover(id);
    }
}
