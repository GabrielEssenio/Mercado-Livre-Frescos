package com.mercadolibre.grupo1.projetointegrador.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Gabriel Essenio
 * teste de integração de products
 */
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
    @author Gabriel Essenio
    Caminho Feliz All Product
     */
    @Test
    @DisplayName("Testando endpoint para retornar todos os produtos cadastrados")
    public void testReturnAllProducts() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/fresh-products/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(9)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("MACA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("UVA")));
    }

    /**
    @author Gabriel Essenio
    Caminho Feliz Product By Category
     */
    @Test
    @DisplayName("Testando se retorna os produtos pela categoria passada pelo parametro")

    public void testReturnProductsByCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/fresh-products/list?status=FRESCO"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("UVA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category", Matchers.is("FRESCO")));
    }

    /**
     @author Gabriel Essenio
      * Testa status quando passar uma categoria que nao existe
     */
    @Test
    @DisplayName("Testando se o status retorna 404 apois tentar mudar status quando passada uma categoria que nao existe")
    public void testStatusReturn404WhenIdCatogyDontExists() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/fresh-products/list?status=FRESCA"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Categoria inválida")));
    }

    /**
     @author Gabriel Essenio
     * REQUISITO 06
      * Testa Listar produtos pela nota de avaliação minima
     */
    @Test
    @DisplayName("Testa Listar produtos pela nota de avaliação minima")
    public void TestReturnProductsByRating() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/fresh-products/rating?rating=4"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("PERA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].averageRating", Matchers.is(5.0)));
    }

    /**
     @author Gabriel Essenio
      * REQUISITO 06
      * Testa Excecao ao passar rating erro pelo parametro
     */
    @Test
    @DisplayName("Testa Excecao ao passar rating erro pelo parametro")
    public void TestRetunExceptionWhenGetProductByRating() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/fresh-products/rating?rating=6"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("A avaliaçao dos produtos sao entre 0 e 5, procurar por notas entre essas")));
    }

    /**
     @author Gabriel Essenio
      * REQUISITO 06
      * Testa se retorna produtos pelo ID
     */
    @Test
    @DisplayName("Testa se retorna produtos pelo ID")
    public void TestReturnProductById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/fresh-products/list-product/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("UVA")));
    }


    /**
     @author Gabriel Essenio
      * REQUISITO 06
      * Testa Excecao ao passar ID de produto que não existe
     */
    @Test
    @DisplayName("Testa Excecao ao passar rating erro pelo parametro")
    public void TestRetunExceptionWhenGetProductByWrongId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/fresh-products/list-product/0"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Nao foi encontrado nenhum produto com esse ID")));
    }

}