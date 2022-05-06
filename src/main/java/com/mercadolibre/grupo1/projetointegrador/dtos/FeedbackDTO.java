package com.mercadolibre.grupo1.projetointegrador.dtos;

import com.mercadolibre.grupo1.projetointegrador.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDTO {
    private Long feedbackId;
    private String productName;
    private String description;
    @Max(value = 5, message = "O valor maximo da nota é 5")@Min(value = 0, message = "O valor minimo da nota é 1")
    private Double rating;
}
