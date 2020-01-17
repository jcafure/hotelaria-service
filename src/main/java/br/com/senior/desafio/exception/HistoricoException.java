package br.com.senior.desafio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HistoricoException extends RuntimeException {

    public HistoricoException(String message) {
        super("Não foi encontrado o histórico desse hospede.");
    }
}
