package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.assembler.CidadeModelDisassembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cidades")
public class CidadeController implements CidadeControllerOpenApi {

    private final CidadeRepository cidadeRepository;
    private final CadastroCidadeService cadastroCidadeService;
    private final CidadeModelAssembler cidadeModelAssembler;
    private final CidadeModelDisassembler cidadeModelDisassembler;

    @GetMapping
    public List<CidadeModel> listar() {
        return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
    }

    @GetMapping("/{id}")
    public CidadeModel buscar(
            @PathVariable Long id) {
        var cidadeModel = cidadeModelAssembler.toModel(cadastroCidadeService.buscarOuFalhar(id));

        cidadeModel.add(linkTo(CidadeController.class)
                .slash(cidadeModel.getId()).withSelfRel());

        //cidadeModel.add(Link.of("api.algafood.local:8080/cidades/1"));

        cidadeModel.add(linkTo(CidadeController.class)
                .withRel("cidades"));

        //cidadeModel.add(Link.of("http://api.algafood.local:8080/cidades", "cidades"));

        cidadeModel.getEstado().add(linkTo(EstadoController.class)
                .slash(cidadeModel.getEstado().getId()).withSelfRel());
        //cidadeModel.getEstado().add(Link.of("http://api.algafood.local:8080/estados/1"));

        return cidadeModel;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(
            @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            var cidade = cidadeModelDisassembler.toDomainObject(cidadeInput);
            cidade = cadastroCidadeService.salvar(cidade);
            var cidadeModel = cidadeModelAssembler.toModel(cidade);

            ResourceUriHelper.addUriResponseHeader(cidadeModel.getId());

            return cidadeModel;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CidadeModel atualizar(
            @PathVariable Long id,
            @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(id);
            cidadeModelDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

            return cidadeModelAssembler
                    .toModel(cadastroCidadeService.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(
            @PathVariable Long id) {
        cadastroCidadeService.remover(id);
    }
}
