package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jfree.util.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/estados")
public class EstadoController {

    private final EstadoRepository estadoRepository;
    private final CadastroEstadoService cadastroEstadoService;
    private final EstadoModelAssembler estadoModelAssembler;
    private final EstadoInputDisassembler estadoInputDisassembler;

    @GetMapping
    public List<EstadoModel> listar() {
        Log.info("Listando estados...");
        return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
    }

    @GetMapping("/{id}")
    public EstadoModel buscar(@PathVariable Long id) {
        Log.info("Buscando estados...");
        return estadoModelAssembler.toModel(cadastroEstadoService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel salvar(@RequestBody @Valid EstadoInput estadoInput) {
        var estado = estadoInputDisassembler.toDomainObject(estadoInput);

        return estadoModelAssembler.toModel(cadastroEstadoService.salvar(estado));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EstadoModel alterar(@RequestBody @Valid EstadoInput estadoInput, @PathVariable Long id) {
        Estado estado = cadastroEstadoService.buscarOuFalhar(id);
        estadoInputDisassembler.copyToDomainObject(estadoInput, estado);

        return estadoModelAssembler.toModel(cadastroEstadoService.alterar(estado, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroEstadoService.remover(id);
    }
}
