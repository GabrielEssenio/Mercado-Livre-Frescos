package com.mercadolibre.grupo1.projetointegrador.exceptions;

public class InvalidRatingException extends RuntimeException {
    public InvalidRatingException(String message) {
        super(message);
    }
}
