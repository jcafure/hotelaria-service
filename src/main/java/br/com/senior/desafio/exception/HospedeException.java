package br.com.senior.desafio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HospedeException extends RuntimeException{

    public HospedeException(String message) {
        super(message);
    }

    public HospedeException(Integer id) {
        super("Nenhuma Hospede com o ID " + id + " encontrado! ");
    }
}
