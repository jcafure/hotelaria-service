package br.com.senior.desafio.service;

import br.com.senior.desafio.model.Historico;

import java.util.Optional;

public interface HistoricoService {

    Optional<Historico> getHistoricoPorHospede(Integer idHospede);
}
