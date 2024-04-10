package com.tom.passwordSafetyBox.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CredentialExistsException extends RuntimeException {
    public CredentialExistsException(String message) {
        super(message);
    }
}
