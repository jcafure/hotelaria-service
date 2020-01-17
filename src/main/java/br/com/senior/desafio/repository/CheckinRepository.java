package br.com.senior.desafio.repository;

import br.com.senior.desafio.model.Checkin;
import br.com.senior.desafio.model.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Integer> {

    @Query("select sum (c.valorTotal) from Checkin c where c.hospede = ?1 AND c.checkout = TRUE")
    Double valorTotalDeHospedagensJaFeitas(Hospede hospede);
}
