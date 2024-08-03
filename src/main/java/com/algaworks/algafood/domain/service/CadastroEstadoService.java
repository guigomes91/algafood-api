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

@RequiredArgsConstructor
@Service
public class CadastroEstadoService {

    public static final String EMPTY_FIELD_NAME = "O campo nome não pode ser vazio!";
    private final EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) {
        if (Strings.isBlank(estado.getNome())) {
            throw new AtributoInvalidoException(EMPTY_FIELD_NAME);
        }

        return estadoRepository.save(estado);
    }

    public Estado alterar(Estado estado, Long id) {
        Estado estadoAtual = estadoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Estado não encontrado com o código %d", id)
                ));

        if (Strings.isBlank(estado.getNome())) {
            throw new AtributoInvalidoException(EMPTY_FIELD_NAME);
        }

        BeanUtils.copyProperties(estado, estadoAtual, "id");
        estadoAtual.setId(id);
        return estadoRepository.save(estadoAtual);
    }

    public void remover(Long id) {
        try {
            Estado estadoAtual = estadoRepository.findById(id)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(
                            String.format("Estado não encontrado com o código %d", id)
                    ));

            estadoRepository.delete(estadoAtual);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Estado de código %d não pode ser removido, pois está em uso", id)
            );
        }
    }

}
