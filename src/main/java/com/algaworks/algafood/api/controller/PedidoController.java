package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoModelDisassembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import com.algaworks.algafood.domain.service.CadastroPedidoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final CadastroPedidoService cadastroPedidoService;
    private final PedidoRepository pedidoRepository;
    private final PedidoModelAssembler pedidoModelAssembler;
    private final PedidoModelDisassembler pedidoModelDisassembler;
    private final PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @GetMapping
    public List<PedidoResumoModel> listar() {
        return pedidoResumoModelAssembler.toCollectionModel(pedidoRepository.findAll());
    }

    @GetMapping("/{pedidoId}")
    public PedidoModel buscar(@PathVariable Long pedidoId) {
        return pedidoModelAssembler.toModel(cadastroPedidoService.buscarOuFalhar(pedidoId));
    }

    /*
        Criação de pedido

        - Endpoint post /pedidos
        - payload com restaurante, forma de pagamento, endereco entrega input, lista de itens input (produtoId, quantidade, observacao)
        - validar os campos, pelo menos um item na lista de itens
        - customizar mensagens de validação
        - validar forma de pagamento por restaurante
        - validar se o produto é do determinado restaurante
        - calcular preco total
        - cliente fixo de id 1
        - para cadastrar os itens do pedido, precisa ser CascadeType.ALL no OneToMany
        - ModelMapper para skip id do itempedido
     */

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PedidoModel salvar(@RequestBody @Valid PedidoInput pedidoInput) {
        var pedido = pedidoModelDisassembler.toDomainObject(pedidoInput);
        var novoPedido = cadastroPedidoService.salvar(pedido);

        return pedidoModelAssembler.toModel(novoPedido);
    }
}
