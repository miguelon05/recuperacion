package com.example.EXAMEN.LP2.RESURECCION.controller.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
