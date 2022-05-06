package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.ProductDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.Feedback;
import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.InvalidRatingException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.NotFoundException;
import com.mercadolibre.grupo1.projetointegrador.repositories.FeedbackRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.ProductRepository;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ListIsEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/*
@author Gabriel Essenio, Weverton Bruno
Construindo service de para tratar as requisitos da busca de produtos
 */

@Service
@RequiredArgsConstructor
public class ProductService {

    /*
    Faz injeção de dependecia do repositorio de produtos
     */
    @Autowired
    private ProductRepository productRepository;

    /*
    @author Gabriel Essenio
    Metodo que chama Repositorio de Produto e retorna todos produtos cadastrados
     */
    public List<ProductDTO> listAllProducts() {
        List<ProductDTO> allProducts = productRepository.findAll().stream()
                .map(product -> new ProductDTO(product.getId(), product.getName(), product.getVolume(), product.getPrice(), product.getCategory(), product.getAverageRating()))
                .collect(Collectors.toList());
        if (allProducts.isEmpty()) {
            throw new ListIsEmptyException("Nenhum produto cadastrado");
        }
        return allProducts;
    }

    /*
    @author Gabriel Essenio
    Metodo que chama Repositorio de Produto e retorna produtos de acordo com a categoria passada pelo parametro)
    Verifica se a lista esta vazia e se a Categoria esta listada no Enum
     */
    public List<ProductDTO> listProductByCategory(ProductCategory productCategory) {
        List<Product> productsByCategory = productRepository.findAllByCategory(productCategory);
        if (productsByCategory.isEmpty()) {
            throw new ListIsEmptyException("Categoria não encontrada");
        }
        return productsByCategory.stream()
                .map(product -> new ProductDTO(product.getId(), product.getName(), product.getVolume(), product.getPrice(), product.getCategory(), product.getAverageRating()))
                .collect(Collectors.toList());
    }

    /**
     * @author Gabriel Essenio
     * REQUISITO 06
     * Lista produtos de acordo com o rating procurado
     */
    public List<ProductDTO> listProductByMinRating(Double rating) {
        if (rating > 5.0 || rating < 0.0) {
            throw new InvalidRatingException("A avaliaçao dos produtos sao entre 0 e 5, procurar por notas entre essas");
        }
        List<Product> productsByMinRating = productRepository.findAllByMinRating(rating);
        if (productsByMinRating.isEmpty()) {
            throw new ListIsEmptyException("Nenhum produto possui essa nota minima");
        }
        return productsByMinRating.stream()
                .map(product -> new ProductDTO(product.getId(), product.getName(), product.getVolume(), product.getPrice(), product.getCategory(), product.getAverageRating()))
                .collect(Collectors.toList());
    }

    /**
     * @author Gabriel Essenio
     * REQUISITO 06
     * Listar produto pelo ID
     */
    public ProductDTO getProductById(Long idProduct) {
        Product productById = productRepository.findById(idProduct).orElseThrow(() -> new NotFoundException("Nao foi encontrado nenhum produto com esse ID"));
        return new ProductDTO(productById.getId(), productById.getName(), productById.getVolume(), productById.getPrice(), productById.getCategory(), productById.getAverageRating());
    }

    /**
     * @param productId
     * @author Weverton Bruno
     */
    public Product findById(Long productId) {
        return productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Produto com ID " + productId + " não encontrado"));
    }
}

