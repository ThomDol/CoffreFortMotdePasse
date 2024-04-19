package com.tom.passwordSafetyBox.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException (String message) {
        super(message);
    }

}