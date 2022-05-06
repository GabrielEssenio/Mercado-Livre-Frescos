package com.mercadolibre.grupo1.projetointegrador.entities;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nayara Coca
 * Criação da entidade products
 * Gerando getters e setters
 */

@Entity
@Builder
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double volume;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private ProductCategory category;
    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private Seller seller;
    @Max(value = 5, message = "O valor maximo da nota é 5")@Min(value = 1, message = "O valor minimo da nota é 1")
    private Double AverageRating;

    @OneToMany(mappedBy = "product")
    private List<Feedback> feedbacks;
}
