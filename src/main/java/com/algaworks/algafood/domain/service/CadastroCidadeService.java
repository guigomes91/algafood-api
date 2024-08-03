package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.AtributoInvalidoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CadastroCidadeService {

    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade) {
        estadoRepository.findById(cidade.getEstado().getId())
            .orElseThrow(() -> new EntidadeNaoEncontradaException(
                    String.format("Estado não encontrado com o código %d", cidade.getEstado().getId())
            ));

        if (Strings.isBlank(cidade.getNome())) {
            throw new AtributoInvalidoException(
                    String.format("O campo nome não pode ser vazio!")
            );
        }

        return cidadeRepository.save(cidade);
    }

    public Cidade alterar(Cidade cidade, Long id) {
        Cidade cidadeAtual = cidadeRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Cidade não encontrada com o código %d", id)
                ));

        estadoRepository.findById(cidade.getEstado().getId())
            .orElseThrow(() -> new EntidadeNaoEncontradaException(
                    String.format("Estado não encontrado com o código %d", cidade.getEstado().getId())
            ));

        if (Strings.isBlank(cidade.getNome())) {
            throw new AtributoInvalidoException(
                    String.format("O campo nome não pode ser vazio!")
            );
        }

        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        cidadeAtual.setId(id);
        return cidadeRepository.save(cidadeAtual);
    }

    public void remover(Long id) {
        try {
            Cidade cidade = cidadeRepository.findById(id)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(
                            String.format("Cidade não encontrada com o código %d", id)
                    ));

            cidadeRepository.delete(cidade);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Cidade de código %d não pode ser removida, pois está em uso", id)
            );
        }
    }
}
