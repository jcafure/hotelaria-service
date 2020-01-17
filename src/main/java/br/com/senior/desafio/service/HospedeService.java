package br.com.senior.desafio.service;

import br.com.senior.desafio.model.Hospede;

import java.util.Optional;

public interface HospedeService {

    Optional<Hospede> recuperaHospedePeloId(Integer id);

    Optional<Hospede> salvarHospede(Hospede hospede);

    Optional<Hospede> atualizarHospede(Hospede hospede);

    void deletarHospede(Integer id);

    Iterable<Hospede> listarHospedes();
}
