package com.mercadolibre.grupo1.projetointegrador.controller;

import com.mercadolibre.grupo1.projetointegrador.dtos.FeedbackDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.Feedback;
import com.mercadolibre.grupo1.projetointegrador.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * @author Gabriel Essenio
 * Controller de Product ,cria os endpoints e trata o retorno de acordo com cada tipo de endpoint
 */
@RestController
@RequestMapping("/api/v1/fresh-products/")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    /**
     * @author Gabriel Essenio
     * @param idProduct
     * @param feedback
     * @return
     * REQUISITO 06
     * Cria Feedback pelo produto
     */
    @PostMapping("/feedback-product/{idProduct}")
    public ResponseEntity<FeedbackDTO> createFeedbackProduct(@PathVariable Long idProduct, @RequestBody Feedback feedback, UriComponentsBuilder uriBuilder){
        FeedbackDTO createFeedbackProduct = feedbackService.createFeedbackProduct(idProduct, feedback);

        URI uri = uriBuilder
                .path("/{idProduct}")
                .buildAndExpand(idProduct)
                .toUri();
        return ResponseEntity.created(uri).body(createFeedbackProduct);
    }

    @GetMapping("/feedback-products/{idProduct}")
        public ResponseEntity<List<FeedbackDTO>> getFeedbacksByProduct(@PathVariable Long idProduct){
        List<FeedbackDTO> getFeedbacksByProduct = feedbackService.getFeedBacksByProduct(idProduct);
        return ResponseEntity.ok().body(getFeedbacksByProduct);
    }

    @PutMapping("/update-feedback/{idFeedback}")
    public ResponseEntity<FeedbackDTO> updateFeedbackProductByIdFeedback(@PathVariable Long idFeedback, @RequestBody Feedback feedback, UriComponentsBuilder uriBuilder){
        FeedbackDTO updateFeedbackProduct = feedbackService.updateFeedbackProductByIdFeedback(idFeedback, feedback);
        URI uri = uriBuilder
                .path("/{idFeedback}")
                .buildAndExpand(idFeedback)
                .toUri();
        return ResponseEntity.created(uri).body(updateFeedbackProduct);
    }
}