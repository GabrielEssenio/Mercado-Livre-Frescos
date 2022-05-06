package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.FeedbackDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.Feedback;
import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ListIsEmptyException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.NotFoundException;
import com.mercadolibre.grupo1.projetointegrador.repositories.FeedbackRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/*
@author Gabriel Essenio
Construindo service de para tratar as requisitos da busca de feedback
 */
@Service
@RequiredArgsConstructor
public class FeedbackService {
    /*
    Faz injeção de dependecia do repositorio de produtos
    */
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    /**
     * @author Gabriel Essenio
     * REQUISITO 06
     * Cria Feedback do produto e calcula media da avaliação
     */
    @Transactional
    public FeedbackDTO createFeedbackProduct(Long idProduct, Feedback feedback) {
        Product productById = productRepository.findById(idProduct).orElseThrow(() -> new NotFoundException("Nao foi encontrado nenhum produto com esse ID"));
        productById.getFeedbacks().add(feedback);
        Double newAverageRating = productById.getFeedbacks().stream().reduce(0D,(subtotal, element) -> subtotal + element.getRating(), Double::sum);
        feedback.setProduct(productById);
        Feedback newFeedback = feedbackRepository.save(feedback);
        productById.setAverageRating(newAverageRating/productById.getFeedbacks().size());
        productRepository.save(productById);
        return new FeedbackDTO(newFeedback.getId(),newFeedback.getProduct().getName(),newFeedback.getDescription(),newFeedback.getRating());
    }

    /**
     * @author Gabriel Essenio
     * REQUISITO 06
     * Listar feedbacks  pelo ID do produto
     */
    public List<FeedbackDTO> getFeedBacksByProduct(Long idProduct) {
        Product productById = productRepository.findById(idProduct).orElseThrow(() -> new NotFoundException("Nao foi encontrado nenhum produto com esse ID"));

        if(productById.getFeedbacks().isEmpty()){
            throw new ListIsEmptyException("Nenhum feedback relacionado a esse produto");
        }
        return productById.getFeedbacks().stream().map(product -> new FeedbackDTO(product.getId(),product.getProduct().getName(),product.getDescription(),product.getRating())).collect(Collectors.toList());
    }

    /**
     * @author Gabriel Essenio
     * REQUISITO 06
     * Atualiza Feedback do produto e calcula media da avaliação
     */
    @Transactional
    public FeedbackDTO updateFeedbackProductByIdFeedback(Long idFeedback, Feedback feedback) {
        Feedback feedbackById = feedbackRepository.findById(idFeedback).orElseThrow(() -> new NotFoundException("Nao foi encontrado nenhum feedback com esse ID"));
        Product productById = feedbackById.getProduct();
        feedbackById.setId(idFeedback);
        feedbackById.setDescription(feedback.getDescription());
        feedbackById.setRating(feedback.getRating());
        Feedback updateFeedback = feedbackRepository.save(feedbackById);
        Double newAverageRating = productById.getFeedbacks().stream().reduce(0D,(subtotal, element) -> subtotal + element.getRating(), Double::sum);
        productById.setAverageRating(newAverageRating/productById.getFeedbacks().size());
        productRepository.save(productById);
        return new FeedbackDTO(updateFeedback.getId(),updateFeedback.getProduct().getName(),updateFeedback.getDescription(),updateFeedback.getRating());
    }
}