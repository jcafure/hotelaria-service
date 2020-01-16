package br.com.senior.desafio.service;

import br.com.senior.desafio.model.Checkin;

import java.util.Optional;


public interface CheckinService {

    Optional<Checkin> createCheckin(Checkin checkin);
}
