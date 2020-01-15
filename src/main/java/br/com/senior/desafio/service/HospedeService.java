package br.com.senior.desafio.service;

import br.com.senior.desafio.model.Hospede;

import java.util.List;
import java.util.Optional;

public interface HospedeService {

    Optional<Hospede> findById(Integer id);

    Optional<Hospede> save(Hospede hospede);

    Optional<Hospede> update(Hospede hospede);

    void delete(Integer id);

    Iterable<Hospede> findAll();

}
