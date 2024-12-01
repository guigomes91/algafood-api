package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CadastroProdutoService {
    public static final String MSG_PRODUTO_EM_USO = "Produto de código %d não pode ser removido, pois está em uso";
    private final ProdutoRepository produtoRepository;
    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Transactional
    public Produto alterar(Produto produto, Long id) {
        produtoRepository.findById(id)
            .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
        produto.setId(id);

        return produtoRepository.save(produto);
    }

    @Transactional
    public void remover(Long id) {
        try {
            produtoRepository.deleteById(id);
            produtoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new ProdutoNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_PRODUTO_EM_USO, id)
            );
        }
    }

    public Produto buscarOuFalhar(Long produtoId) {
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
    }
}
