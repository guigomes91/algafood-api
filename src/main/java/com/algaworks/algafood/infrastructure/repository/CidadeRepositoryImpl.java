package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Slf4j
@Component
public class CidadeRepositoryImpl implements CidadeRepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Cidade> listar() {
        return manager.createQuery("from Cidade", Cidade.class)
                .getResultList();
    }

    @Transactional
    @Override
    public Cidade salvar(Cidade cidade) {
        log.info("Salvando cidade {}", cidade.getNome());
        return manager.merge(cidade);
    }

    @Override
    public Cidade buscar(Long id) {
        return manager.find(Cidade.class, id);
    }

    @Override
    @Transactional
    public void remover(Cidade cidade) {
        cidade = this.buscar(cidade.getId());
        manager.remove(cidade);
        log.info("Cidade {} removida com sucesso!", cidade.getNome());
    }
}
