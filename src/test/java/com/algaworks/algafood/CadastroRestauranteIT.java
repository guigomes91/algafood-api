package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroRestauranteIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private int totalDeRestaurantes;

    @BeforeEach
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes";

        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    void deveRetornarStatus200_QuandoConsultarRestaurantes() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveConterDoisRestaurantes_QuandoConsultarRestaurantes() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", hasSize(this.totalDeRestaurantes))
                .body("nome", hasItems("Fafa de Belem", "Manoel Gomi"));
    }

    @Test
    void deveRetornarStatus201_QuandoCadastrarRestaurante() {
        var cadastroCozinhaJson = ResourceUtils.getContentFromResource("/json/cadastro-restaurante.json");

        given()
                .body(cadastroCozinhaJson)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    private void prepararDados() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome("Fafa de Belem");
        restaurante.setTaxaFrete(BigDecimal.valueOf(20));

        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Tailandesa");
        var cozinhaNew = cozinhaRepository.save(cozinha);
        restaurante.setCozinha(cozinhaNew);

        restauranteRepository.save(restaurante);

        restaurante = new Restaurante();
        restaurante.setNome("Manoel Gomi");
        restaurante.setTaxaFrete(BigDecimal.TEN);

        cozinha = new Cozinha();
        cozinha.setNome("Tailandesa");
        cozinhaNew = cozinhaRepository.save(cozinha);
        restaurante.setCozinha(cozinhaNew);

        restauranteRepository.save(restaurante);

        this.totalDeRestaurantes = (int) restauranteRepository.count();
    }
}
