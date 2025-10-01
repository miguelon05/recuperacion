package com.example.EXAMEN.LP2.RESURECCION.controller.exceptions;

public class BussinessException extends RuntimeException {
    public BussinessException(String message) {
        super(message);
    }
    public BussinessException(String message, Throwable cause) {
        super(message, cause);
    }
    public BussinessException(Throwable cause) {
        super(cause);
    }
}
