package br.com.senior.desafio.repository;

import br.com.senior.desafio.model.Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Integer> {

    @Query("SELECT h from Historico h where h.hospede.id = ?1")
    Optional<Historico> getHistoricoByHospede(Integer hospedeId);
}
