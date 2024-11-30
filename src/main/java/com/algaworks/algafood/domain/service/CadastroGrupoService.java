package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CadastroGrupoService {
    public static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removido, pois está em uso";
    private final GrupoRepository grupoRepository;
    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public Grupo alterar(Grupo grupo, Long id) {
        grupoRepository.findById(id)
            .orElseThrow(() -> new GrupoNaoEncontradoException(id));
        grupo.setId(id);

        return grupoRepository.save(grupo);
    }

    @Transactional
    public void remover(Long id) {
        try {
            grupoRepository.deleteById(id);
            grupoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_GRUPO_EM_USO, id)
            );
        }
    }

    public Grupo buscarOuFalhar(Long grupoId) {
        return grupoRepository.findById(grupoId)
                .orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
    }
}
