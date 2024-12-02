package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.CadastroPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final CadastroPedidoService cadastroPedidoService;
    private final PedidoRepository pedidoRepository;
    private final PedidoModelAssembler pedidoModelAssembler;

    @GetMapping
    public List<PedidoModel> listar() {
        return pedidoModelAssembler.toCollectionModel(pedidoRepository.findAll());
    }

    @GetMapping("/{pedidoId}")
    public PedidoModel buscar(@PathVariable Long pedidoId) {
        return pedidoModelAssembler.toModel(cadastroPedidoService.buscarOuFalhar(pedidoId));
    }
}
