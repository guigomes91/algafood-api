package com.algaworks.algafood;

import com.algaworks.algafood.modelo.Cliente;
import com.algaworks.algafood.service.AtivacaoClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MeuPrimeiroController {

    private AtivacaoClienteService ativacaoClienteService;

    public MeuPrimeiroController(AtivacaoClienteService ativacaoClienteService) {
        this.ativacaoClienteService = ativacaoClienteService;
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        Cliente gui = new Cliente("Gui", "gui@email.com", "199989-8878");
        ativacaoClienteService.ativar(gui);
        return "Ola mundo";
    }
}
