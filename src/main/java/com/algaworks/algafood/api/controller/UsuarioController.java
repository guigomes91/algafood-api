package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.assembler.UsuarioModelDisassembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.api.model.input.UsuarioSenhaInput;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final CadastroUsuarioService cadastroUsuarioService;
    private final UsuarioModelAssembler usuarioModelAssembler;
    private final UsuarioModelDisassembler usuarioModelDisassembler;

    @GetMapping
    public List<UsuarioModel> listar() {
        return usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll());
    }

    @GetMapping("/{id}")
    public UsuarioModel buscar(@PathVariable Long id) {
        return usuarioModelAssembler.toModel(cadastroUsuarioService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel salvar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        try {
            final var usuario = usuarioModelDisassembler.toDomainObject(usuarioInput);
            return usuarioModelAssembler.toModel(cadastroUsuarioService.salvar(usuario));
        } catch (ConstraintViolationException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioModel alterar(@RequestBody @Valid UsuarioInput usuarioInput, @PathVariable Long id) {
        try {
            Usuario usuarioAtual = cadastroUsuarioService.buscarOuFalhar(id);
            usuarioModelDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
            return usuarioModelAssembler.toModel(cadastroUsuarioService.salvar(usuarioAtual));
        } catch (GrupoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@RequestBody @Valid UsuarioSenhaInput usuarioInput, @PathVariable Long id) {
        Usuario usuarioAtual = cadastroUsuarioService.buscarOuFalhar(id);
        cadastroUsuarioService.alterarSenha(usuarioAtual, usuarioInput.getSenhaAtual(), usuarioInput.getNovaSenha());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroUsuarioService.remover(id);
    }
}
