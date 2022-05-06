package com.mercadolibre.grupo1.projetointegrador.repositories;

import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import com.mercadolibre.grupo1.projetointegrador.entities.Feedback;
import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Gabriel Essenio
 * Criação repositório de feedback
 * REQUISITO 06
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
