package com.szczynk.simsppob.exception;

import java.io.Serial;

public class ResourceAlreadyExisted extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceAlreadyExisted(String msg) {
        super(msg);
    }
}
