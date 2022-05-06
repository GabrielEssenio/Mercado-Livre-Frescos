package com.mercadolibre.grupo1.projetointegrador.repositories;

import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Nayara Coca
 * Criação repositório de product
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * @author Gabriel Essenio
     * Metodos que fazem query de acordo com o nome do metodo
     */
    List<Product> findAll();
    List<Product> findAllByCategory(ProductCategory productCategory);

    /**
     * @author Gabriel Essenio
     * REQUISITO 06
     */
    @Query(value =
            "SELECT p FROM Product p " +
                    "WHERE p.AverageRating > :ratingMin ")
    List<Product> findAllByMinRating(Double ratingMin);
}