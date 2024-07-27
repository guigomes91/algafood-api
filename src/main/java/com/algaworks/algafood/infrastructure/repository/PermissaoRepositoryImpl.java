package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Permissao> listar() {
        return manager.createQuery("from Permissao", Permissao.class)
                .getResultList();
    }

    @Transactional
    @Override
    public Permissao salvar(Permissao permissao) {
        return manager.merge(permissao);
    }

    @Override
    public Permissao buscar(Long id) {
        return manager.find(Permissao.class, id);
    }

    @Override
    @Transactional
    public void remover(Permissao permissao) {
        permissao = this.buscar(permissao.getId());
        manager.remove(permissao);
    }
}
