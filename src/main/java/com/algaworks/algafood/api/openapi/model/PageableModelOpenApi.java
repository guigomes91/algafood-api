package com.algaworks.algafood.api.openapi.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PageableModelOpenApi {

    private int page;
    private int size;
    private List<String> sort;
}
