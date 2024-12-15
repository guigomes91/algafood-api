package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoModelDisassembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final EmissaoPedidoService emissaoPedidoService;
    private final PedidoRepository pedidoRepository;
    private final PedidoModelAssembler pedidoModelAssembler;
    private final PedidoModelDisassembler pedidoModelDisassembler;
    private final PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @GetMapping
    public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
        final var pedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
        var pedidosModel = pedidoResumoModelAssembler.toCollectionModel(pedidos.getContent());

        return new PageImpl<>(pedidosModel, pageable, pedidos.getTotalElements());
    }

    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable String codigoPedido) {
        return pedidoModelAssembler.toModel(emissaoPedidoService.buscarOuFalhar(codigoPedido));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PedidoModel salvar(@RequestBody @Valid PedidoInput pedidoInput) {
        var pedido = pedidoModelDisassembler.toDomainObject(pedidoInput);
        pedido.setCliente(new Usuario());
        pedido.getCliente().setId(1L);
        var novoPedido = emissaoPedidoService.salvar(pedido);

        return pedidoModelAssembler.toModel(novoPedido);
    }
}
