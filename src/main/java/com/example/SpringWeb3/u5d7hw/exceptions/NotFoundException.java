package com.example.SpringWeb3.u5d7hw.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(int id) {
        super(id + " non trovato!");
    }

    public NotFoundException(UUID id, String entityType) {
        super(entityType + " with id " + id + " non trovato!");
    }
}