package br.com.senior.desafio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CheckinException extends RuntimeException {

    public CheckinException(String message) {
        super(message);
    }

}
