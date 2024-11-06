package com.algaworks.algafood;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CadastroCozinhaIntegrationTests {

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Test
	void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
		//cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		//ação
		novaCozinha = cadastroCozinhaService.salvar(novaCozinha);

		//validação
		assertNotNull(novaCozinha);
	}

	@Test
	void deveFalhar_QuandoCadastrarCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		ConstraintViolationException erroEsperado =
				org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class, () -> {
					cadastroCozinhaService.salvar(novaCozinha);
				});

		assertThat(erroEsperado).isNotNull();
	}

	@Test
	void deveFalhar_QuandoExcluirCozinhaEmUso() {
		EntidadeEmUsoException erroEsperado =
				org.junit.jupiter.api.Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
					cadastroCozinhaService.excluir(1L);
				});

		assertThat(erroEsperado).isNotNull();
	}

	@Test
	void deveFalhar_QuandoExcluirCozinhaInexistente() {
		CozinhaNaoEncontradaException erroEsperado =
				org.junit.jupiter.api.Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
					cadastroCozinhaService.excluir(10L);
				});

		assertThat(erroEsperado).isNotNull();
	}

}
