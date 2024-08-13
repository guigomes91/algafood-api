package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastroRestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não existe cadastro de cozinha com código %d", cozinhaId)
                ));

        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }

    public Restaurante alterar(Restaurante restaurante, Long id) {
        Long cozinhaId = restaurante.getCozinha().getId();
        cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() ->  new EntidadeNaoEncontradaException(
                        String.format("Não existe cadastro de cozinha com o código %d", cozinhaId)
                ));

        Restaurante restauranteAtual = restauranteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não existe cadastro de restaurante com o código %d", id)
                ));

        BeanUtils.copyProperties(restaurante, restauranteAtual,
                "id", "formasPagamento", "endereco", "dataCadastro");
        return restauranteRepository.save(restauranteAtual);
    }
}
