package com.mercadolibre.grupo1.projetointegrador.unit;

import com.mercadolibre.grupo1.projetointegrador.dtos.FeedbackDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.ProductDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.Feedback;
import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.exceptions.InvalidRatingException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ListIsEmptyException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.NotFoundException;
import com.mercadolibre.grupo1.projetointegrador.repositories.FeedbackRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.ProductRepository;
import com.mercadolibre.grupo1.projetointegrador.services.FeedbackService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyLong;

/**
 * @author Gabriel Essenio
 * Teste unitario de Service de Feedback
 */
@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {


    @Mock
    private ProductRepository productRepository;

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    private Feedback gerarFeedback(Long id, Double nota) {
        Feedback feedback = new Feedback();
        feedback.setId(id);
        feedback.setDescription("Produto de qualidade");
        feedback.setRating(nota);
        return feedback;
    }

    private Product gerarProduct() {
        Feedback feedback1 = gerarFeedback(1L, 4.0);
        Feedback feedback2 = gerarFeedback(2L, 3.0);
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product1");
        product1.setVolume(20.0);
        product1.setPrice(BigDecimal.valueOf(350));
        product1.setCategory(ProductCategory.FRESCO);
        product1.setAverageRating(4.0);
        product1.setFeedbacks(Arrays.asList(feedback1, feedback2));
        return product1;
    }

    /**
     * @author Gabriel Essenio
     * REQUISITO 06
     * Teste Exceçao e mensagem quando nao exite produto passado no parametro
     */

    @Test
    @DisplayName("Testa criar um feedback para um producto que nao existe")
    public void testReturnExceptionWhenIdProductNotExist() {
        Feedback feedback = gerarFeedback(1L, 3.0);
        Mockito.when(productRepository.findById(0L)).thenReturn(Optional.empty());
        Throwable productNotFound = Assertions.assertThrows(NotFoundException.class, () -> feedbackService.createFeedbackProduct(0L, feedback));
        Assertions.assertEquals(productNotFound.getMessage(), "Nao foi encontrado nenhum produto com esse ID");
    }

    /**
     * @author Gabriel Essenio
     * REQUISITO06
     * Testa ao criar um feedback do produto, se é calculado a media da nota do produto
     */
//    @Transactional
//    @Test
//    @DisplayName("Testa ao criar um feedback do produto, se é calculado a media da nota do produto")
//    public void testListProductByCategory() {
//        Feedback feedback1 = gerarFeedback(1L, 2.0);
//        Product product = gerarProduct();
//        product.getFeedbacks().add(feedback1);
//        feedback1.setProduct(product);
//        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
//        Mockito.when(feedbackRepository.save(feedback1)).thenReturn(feedback1);
//        FeedbackDTO feedbackDTO = feedbackService.createFeedbackProduct(1L, feedback1);
//        Assertions.assertEquals(2.0, feedbackDTO.getRating());
//        Assertions.assertEquals(3.0, product.getAverageRating());
//    }

    /**
     * @author Gabriel Essenio
     * REQUISITO 06
     * Teste Exceçao e mensagem quando nao exite produto passado no parametro
     */
    @Test
    @DisplayName("Testa pegar ou atualizar um feedback de um producto que nao existe")
    public void testReturnExceptionWhenGetProductNotExist() {
        Mockito.when(productRepository.findById(0L)).thenReturn(Optional.empty());
        Throwable productNotFound = Assertions.assertThrows(NotFoundException.class, () -> feedbackService.getFeedBacksByProduct(0L));
        Assertions.assertEquals(productNotFound.getMessage(), "Nao foi encontrado nenhum produto com esse ID");
        Feedback feedback1 = gerarFeedback(1L, 2.0);
        Throwable updateFeedbackNotFound = Assertions.assertThrows(NotFoundException.class, () -> feedbackService.updateFeedbackProductByIdFeedback(0L, feedback1));
        Assertions.assertEquals(updateFeedbackNotFound.getMessage(), "Nao foi encontrado nenhum feedback com esse ID");
    }

    /**
     * @author Gabriel Essenio
     * REQUISITO06
     * Testa ao pegar os feedbacks de produto, com ID não criado,se retorna os exceçao esperada
     */
    @Test
    @DisplayName("Testa ao pegar os feedbacks de produto,se retorna os corretamente")
    public void testReturnExceptionFeedbacksByProduct() {
        Mockito.when(productRepository.findById(8L)).thenReturn(Optional.empty());
        Throwable feedbacksByProduct = Assertions.assertThrows(NotFoundException.class, () -> feedbackService.getFeedBacksByProduct(8L));
        Assertions.assertEquals(feedbacksByProduct.getMessage(), "Nao foi encontrado nenhum produto com esse ID");
    }

    /**
     * @author Gabriel Essenio
     * REQUISITO06
     * Testa ao pegar os feedbacks de produto,se retorna os corretamente
     */
//    @Test
//    @Transactional
//    @DisplayName("Testa ao pegar os feedbacks de produto,se retorna os corretamente")
//    public void testReturnListFeedbacksByProduct(){
////        Feedback feedback1 = gerarFeedback(null,2.0);
//        Product product = gerarProduct();
////        feedbackService.createFeedbackProduct(1L, feedback1);
//        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
////        Mockito.when(feedbackRepository.save(feedback1)).thenReturn(feedback1);
//        List<FeedbackDTO> listFeedbacks = feedbackService.getFeedBacksByProduct(1L);
//        Assertions.assertEquals(listFeedbacks.size(),2);
//    }
    /**
     * @author Gabriel Essenio
     * REQUISITO06
     * Testa atualizar o feedbacks passado pelo parametro
     */
//    @Test
//    @Transactional
//    @DisplayName("Testa atualizar o feedbacks passado pelo parametro")
//    public void testUpdateFeedbackById() {
//        Feedback feedback1 = gerarFeedback(1L, 2.0);
//        Mockito.when(feedbackRepository.findById(1L)).thenReturn(Optional.of(feedback1));
//        Mockito.when(feedbackRepository.save(feedback1)).thenReturn(feedback1);
//        FeedbackDTO feedbackDTO = feedbackService.updateFeedbackProductByIdFeedback(1L, feedback1);
//        Assertions.assertEquals(feedbackDTO.getRating(), 2.0);
//    }
}
