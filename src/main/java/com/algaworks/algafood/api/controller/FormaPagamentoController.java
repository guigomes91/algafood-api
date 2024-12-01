package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoModelDisassembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    private final FormaPagamentoRepository formaPagamentoRepository;
    private final CadastroFormaPagamentoService cadastroFormaPagamentoService;
    private final FormaPagamentoModelAssembler formaPagamentoModelAssembler;
    private final FormaPagamentoModelDisassembler formaPagamentoModelDisassembler;

    @GetMapping
    public List<FormaPagamentoModel> listar() {
        return formaPagamentoModelAssembler.toCollectionModel(formaPagamentoRepository.findAll());
    }

    @GetMapping("/{id}")
    public FormaPagamentoModel buscar(@PathVariable Long id) {
        return formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel salvar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        try {
            final var formaPagamento = formaPagamentoModelDisassembler.toDomainObject(formaPagamentoInput);
            return formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.salvar(formaPagamento));
        } catch (FormaPagamentoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FormaPagamentoModel alterar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput, @PathVariable Long id) {
        try {
            FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(id);
            formaPagamentoModelDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamento);

            return formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.salvar(formaPagamento));
        } catch (FormaPagamentoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroFormaPagamentoService.excluir(id);
    }
}