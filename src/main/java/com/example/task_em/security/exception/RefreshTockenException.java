package com.example.task_em.security.exception;

import java.text.MessageFormat;

public class RefreshTockenException extends RuntimeException{

    public RefreshTockenException(String token, String message) {
        super(MessageFormat.format("Error trying to refresh token {0}: {1}", token, message));
    }

    public RefreshTockenException(String message) {
        super(message);
    }
}

