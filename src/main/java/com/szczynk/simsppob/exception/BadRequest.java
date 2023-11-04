package com.szczynk.simsppob.exception;

import java.io.Serial;

public class BadRequest extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public BadRequest(String message) {
        super(message);
    }
}
