package com.mercadolibre.grupo1.projetointegrador.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * @author Nayara Coca, Gabriel Essenio, Ederson Rodrigues
 * trata exceções do controller
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDTO {
    private Integer status;
    private String message;
    private Instant timestamp;
    private String path;

    /**
     * @author Gabriel Essenio, Ederson Rodrigues Araujo
     * erro 404 Not Found
     */
    public static ExceptionDTO notFound(String message, String path) {
        return new ExceptionDTO(HttpStatus.NOT_FOUND.value(), message, Instant.now(), path);
    }

    //erro 400
    public static ExceptionDTO badRequest(String message, String path) {
        return new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), message, Instant.now(), path);
    }
}
