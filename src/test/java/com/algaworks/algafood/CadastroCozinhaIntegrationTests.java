package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CadastroCozinhaIntegrationTests {

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Test
	void testCadastroCozinhaComSucesso() {
		//cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		//ação
		novaCozinha = cadastroCozinhaService.salvar(novaCozinha);

		//validação
		assertNotNull(novaCozinha);
	}

	@Test
	void testDeveFalharAoCadastrarCozinha_QuandoSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		ConstraintViolationException erroEsperado =
				org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class, () -> {
					cadastroCozinhaService.salvar(novaCozinha);
				});

		assertThat(erroEsperado).isNotNull();
	}

}
