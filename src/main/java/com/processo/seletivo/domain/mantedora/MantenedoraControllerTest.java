package com.processo.seletivo.domain.mantedora;

import com.processo.seletivo.SeletivoApplication;
import com.processo.seletivo.core.exception.CustomDependencyException;
import com.processo.seletivo.core.exception.CustomInternalException;
import com.processo.seletivo.core.exception.CustomNotFoundException;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasToString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeletivoApplication.class)
@WebAppConfiguration
@ContextConfiguration
public class MantenedoraControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    MantenedoraRepository mantenedoraRepository;

    Mantenedora mantenedoraValida, mantenedoraInvalida;
    private MockMvc mockMvc;
    private List<Mantenedora> mantenedoraList;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        RestAssuredMockMvc.mockMvc(mockMvc);

        this.mantenedoraList = new ArrayList<>();

        mantenedoraValida = new Mantenedora();
        mantenedoraValida.setNome("Mantenedora 1");
        mantenedoraValida.setCodigo("BC1");
        mantenedoraValida.setNumeroFiscal("12345");
        mantenedoraValida.setEndereco("Avenida Universitária");

        this.mantenedoraList.add(mantenedoraValida);

        mantenedoraInvalida = new Mantenedora();
        mantenedoraInvalida.setNome(RandomStringUtils.randomAlphabetic(90));
        mantenedoraInvalida.setCodigo(RandomStringUtils.randomAlphabetic(10));
        mantenedoraInvalida.setEndereco(RandomStringUtils.randomAlphabetic(60));

        this.mantenedoraList.add(mantenedoraInvalida);
    }

    @After
    public void finish() throws Exception {
    }

    @Test
    public void listarComListaVazia() throws Exception {
        List<Mantenedora> list = new ArrayList<>();

        when(mantenedoraRepository.findAll()).thenReturn(list);

        io.restassured.module.mockmvc.RestAssuredMockMvc.get("/mantenedora")
                .then()
                .statusCode(200)
                .body(String.valueOf("content".toString()), hasToString("[]"));
    }

    @Test
    public void cadastrarItemComDadosValidos() {
        when(mantenedoraRepository.save(mantenedoraValida)).thenReturn(mantenedoraValida);

        io.restassured.module.mockmvc.RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(mantenedoraValida)
                .when()
                .post("/mantenedora")
                .then()
                .statusCode(200);
    }

    @Test
    public void cadastrarItemComDadosInvalidos() {
        doThrow(CustomInternalException.class).when(mantenedoraRepository).save(mantenedoraInvalida);

        io.restassured.module.mockmvc.RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(mantenedoraInvalida)
                .when()
                .post("/mantenedora")
                .then()
                .statusCode(400);
    }

    @Test
    public void cadastrarItemComDadosDuplicados() {
        when(mantenedoraRepository.findByNomeOrCodigo(mantenedoraValida.getNome(), mantenedoraValida.getCodigo())).thenReturn(this.mantenedoraList);

        io.restassured.module.mockmvc.RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(mantenedoraValida)
                .when()
                .post("/mantenedora")
                .then()
                .statusCode(409);
    }

    @Test
    public void alterarItemCadastradoComDadosValidos() {
        when(mantenedoraRepository.save(mantenedoraValida)).thenReturn(mantenedoraValida);

        io.restassured.module.mockmvc.RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(mantenedoraValida)
                .when()
                .put("/mantenedora")
                .then()
                .statusCode(200);
    }

    @Test
    public void alterarItemCadastradoComDadosInvalidos() {
        doThrow(CustomInternalException.class).when(mantenedoraRepository).save(mantenedoraInvalida);

        io.restassured.module.mockmvc.RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(mantenedoraInvalida)
                .when()
                .put("/mantenedora")
                .then()
                .statusCode(400);
    }

    @Test
    public void alterarItemCadastradoComDadosDuplicados() {
        mantenedoraValida.setId(1);

        when(mantenedoraRepository.findByIdAndNomeOrCodigo(mantenedoraValida.getId(), mantenedoraValida.getNome(), mantenedoraValida.getCodigo())).thenReturn(mantenedoraList);

        io.restassured.module.mockmvc.RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(mantenedoraValida)
                .when()
                .put("/mantenedora")
                .then()
                .statusCode(409);
    }

    @Test
    public void listarComListaCheia() {
        when(mantenedoraRepository.findAll()).thenReturn(this.mantenedoraList);

        List<Mantenedora> response = io.restassured.module.mockmvc.RestAssuredMockMvc
                .when()
                .get("/mantenedora")
                .then()
                .statusCode(200)
                .body("content.codigo", hasItems("BC1"))
                .extract()
                .path("content");
    }

    @Test
    public void buscarItemExistentePeloID() {
        when(mantenedoraRepository.findOne(mantenedoraValida.getId())).thenReturn(mantenedoraValida);

        io.restassured.module.mockmvc.RestAssuredMockMvc.get("/mantenedora/{id}", mantenedoraValida.getId())
                .then()
                .statusCode(200);
    }

    @Test
    public void buscarItemInexistentePeloID() {
        when(mantenedoraRepository.findOne(mantenedoraValida.getId())).thenThrow(CustomNotFoundException.class);

        io.restassured.module.mockmvc.RestAssuredMockMvc.get("/mantenedora/{id}", mantenedoraValida.getId())
                .then()
                .statusCode(404);
    }

    @Test
    public void alterarItemInexistente() {
        when(mantenedoraRepository.findOne(mantenedoraValida.getId())).thenThrow(CustomNotFoundException.class);

        io.restassured.module.mockmvc.RestAssuredMockMvc.get("/mantenedora/alterar/{id}", mantenedoraValida.getId())
                .then()
                .statusCode(404);
    }

    @Test
    public void excluirItemComDependencia() {
        when(mantenedoraRepository.findOne(mantenedoraValida.getId())).thenReturn(mantenedoraValida);
        doThrow(CustomDependencyException.class).when(mantenedoraRepository).delete(mantenedoraValida.getId());

        io.restassured.module.mockmvc.RestAssuredMockMvc.delete("/mantenedora/{id}", mantenedoraValida.getId())
                .then()
                .statusCode(412);
    }

    @Test
    public void excluirItemCadastrado() {
        when(mantenedoraRepository.findOne(mantenedoraValida.getId())).thenReturn(mantenedoraValida);
        doNothing().when(mantenedoraRepository).delete(mantenedoraValida.getId());

        io.restassured.module.mockmvc.RestAssuredMockMvc.delete("/mantenedora/{id}", mantenedoraValida.getId())
                .then()
                .statusCode(200);
    }

    @Test
    public void excluirItemInexistente() {
        when(mantenedoraRepository.findOne(mantenedoraValida.getId())).thenThrow(CustomNotFoundException.class);
        doNothing().when(mantenedoraRepository).delete(mantenedoraValida);

        io.restassured.module.mockmvc.RestAssuredMockMvc.delete("/mantenedora/{id}", mantenedoraValida.getId())
                .then()
                .statusCode(404);
    }

//    @Test
//    public void buscarMantenedoraPeloStatusAtivoListaCheia() {
//        when(mantenedoraRepository.findBySituacaoIn(SituacaoEnum.ATIVO)).thenReturn(mantenedoraList);
//
//        io.restassured.module.mockmvc.RestAssuredMockMvc.get("/mantenedora/situacao-ativo")
//                .then()
//                .statusCode(200)
//                .body("content.codigo", hasItems("BC1"))
//                .extract()
//                .path("content");
//    }

//    @Test
//    public void buscarMantenedoraPeloStatusAtivoListaVazia() {
//        List<Mantenedora> list = new ArrayList<>();
//        when(mantenedoraRepository.findAll()).thenReturn(list);
//
//        io.restassured.module.mockmvc.RestAssuredMockMvc.get("/mantenedora/situacao-ativo")
//                .then()
//                .statusCode(200)
//                .body(String.valueOf("content".toString()), hasToString("[]"));
//    }

}
