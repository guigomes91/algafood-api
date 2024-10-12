package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.exceptionhandler.Problema;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private final CidadeRepository cidadeRepository;
    private final CadastroCidadeService cadastroCidadeService;

    @GetMapping
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cidade buscar(@PathVariable Long id) {
        return cadastroCidadeService.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade salvar(@RequestBody Cidade cidade) {
        try {
            return cadastroCidadeService.salvar(cidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cidade alterar(@RequestBody Cidade cidade, @PathVariable Long id) {
        try {
            Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(id);
            BeanUtils.copyProperties(cidade, cidadeAtual, "id");

            return cadastroCidadeService.salvar(cidadeAtual);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroCidadeService.remover(id);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> tratarEstadoNaoEncontradoException(
            EntidadeNaoEncontradaException e) {

        var problema = Problema.builder()
                .dataHora(LocalDateTime.now())
                .mensagem(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(problema);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> tratarNegocioException(
            NegocioException e) {

        var problema = Problema.builder()
                .dataHora(LocalDateTime.now())
                .mensagem(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(problema);
    }
}
