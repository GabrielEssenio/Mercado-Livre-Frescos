package com.mercadolibre.grupo1.projetointegrador.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.grupo1.projetointegrador.entities.Feedback;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author Gabriel Essenio
 * teste de integração de products
 * REQUESITO 06
 */
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private Feedback gerarFeedback(Double nota) {
        Feedback feedback = new Feedback();
        feedback.setDescription("Produto de qualidade");
        feedback.setRating(nota);
        return feedback;
    }

    /**
     @author Gabriel Essenio
     REQUESITO 06
     Testa se retorna cria um feedback para um produto
     */
    @Test
    @WithMockUser(username = "customer1", roles = {"CUSTOMER"})
    @DisplayName("Testa se retorna cria um feedback para um produto")
    public void testReturnProdutsByRating() throws Exception {

        Feedback feedback = gerarFeedback(4.2);
        String payload = objectMapper.writeValueAsString(feedback);

        mockMvc.perform(MockMvcRequestBuilders
                .post("http://localhost:8080/api/v1/fresh-products/feedback-product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName", Matchers.is("UVA")));
    }
    /**
    @author Gabriel Essenio
    REQUESITO 06
    Testa se retorna atualiza um feedback de um produto pelo ID do feedback
     */
    @Test
    @WithMockUser(username = "customer1", roles = {"CUSTOMER"})
    @DisplayName("Testa se retorna atualiza um feedback de um produto pelo ID do feedback")
    public void testReturnNewFeedbackAtualizado() throws Exception {

        Feedback feedback = gerarFeedback(4.2);
        String payload = objectMapper.writeValueAsString(feedback);

        mockMvc.perform(MockMvcRequestBuilders
                .put("http://localhost:8080/api/v1/fresh-products/update-feedback/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Produto de qualidade")));
    }

    /**
     @author Gabriel Essenio
      * REQUISITO 06
      * Testa pegar todos feedbacks pelo id do produto
     */
    @Test
    @WithMockUser(username = "customer1", roles = {"CUSTOMER"})
    @DisplayName("Testa pegar todos feedbacks pelo id do produto")
    public void TestRetunFeedbacksByProdutoId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/fresh-products/feedback-products/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName", Matchers.is("UVA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(3)));
    }
}
