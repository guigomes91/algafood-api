package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.AtributoInvalidoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CadastroEstadoService {

    private final EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) {
        if (Strings.isBlank(estado.getNome())) {
            throw new AtributoInvalidoException(
                    String.format("O campo nome não pode ser vazio!")
            );
        }

        return estadoRepository.salvar(estado);
    }

    public Estado alterar(Estado estado, Long id) {
        Estado estadoAtual = estadoRepository.buscar(id);

        if (Objects.isNull(estadoAtual)) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Estado não encontrado com o código %d", id)
            );
        }

        if (Strings.isBlank(estado.getNome())) {
            throw new AtributoInvalidoException(
                    String.format("O campo nome não pode ser vazio!")
            );
        }

        BeanUtils.copyProperties(estado, estadoAtual, "id");
        estadoAtual.setId(id);
        return estadoRepository.salvar(estadoAtual);
    }

    public void remover(Long id) {
        try {
            Estado estadoAtual = estadoRepository.buscar(id);

            if (Objects.isNull(estadoAtual)) {
                throw new EntidadeNaoEncontradaException(
                        String.format("Estado não encontrado com o código %d", id)
                );
            }

            estadoRepository.remover(estadoAtual);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Estado de código %d não pode ser removido, pois está em uso", id)
            );
        }
    }

}
