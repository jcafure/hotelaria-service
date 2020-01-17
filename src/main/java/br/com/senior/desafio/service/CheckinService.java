package br.com.senior.desafio.service;

import br.com.senior.desafio.model.Checkin;

import java.util.List;
import java.util.Optional;


public interface CheckinService {

    Optional<Checkin> checkinEntrada(Checkin checkin);

    Optional<Checkin> checkinSaida(Checkin checkin);

    Optional<Checkin> getCheckinPeloId(Integer id);

    List<Checkin> buscarTodosOsHospedesQueNaoFizeramCheckout();

    List<Checkin> buscarTodosOsHospedesQueJaFizeramCheckout();

}
