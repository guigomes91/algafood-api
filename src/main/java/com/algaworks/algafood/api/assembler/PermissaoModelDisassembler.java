package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.api.model.input.PermissaoInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PermissaoModelDisassembler {

    private final ModelMapper modelMapper;

    public Permissao toDomainObject(PermissaoInput permissaoInput) {
        return modelMapper.map(permissaoInput, Permissao.class);
    }

    public void copyToDomainObject(PermissaoInput permissaoInput, Permissao permissao) {
        modelMapper.map(permissaoInput, permissao);
    }
}
