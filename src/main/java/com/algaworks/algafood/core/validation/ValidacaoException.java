package com.algaworks.algafood.core.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@SuppressWarnings("SerializableHasSerializationMethods")
@AllArgsConstructor
@Getter
public final class ValidacaoException extends RuntimeException {

    private static final long serialVersionUID = 1l;
    private BindingResult bindingResult;
}
