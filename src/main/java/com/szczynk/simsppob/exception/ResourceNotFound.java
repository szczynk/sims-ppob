package com.szczynk.simsppob.exception;

import java.io.Serial;

public class ResourceNotFound extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceNotFound(String resource) {
        super(resource + " not found");
    }
}
