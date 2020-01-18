package br.com.senior.desafio.repository;

import br.com.senior.desafio.model.Checkin;
import br.com.senior.desafio.model.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Integer> {

    @Query("SELECT SUM (c.valorTotal) from Checkin c WHERE c.hospede = ?1 AND c.checkout = TRUE")
    Double valorTotalDeHospedagensJaFeitas(Hospede hospede);

    @Query("SELECT c FROM Checkin c WHERE c.checkout = FALSE ")
    List<Checkin> buscarTodosOsHospedesQueNaoFizeramCheckout();

    @Query("SELECT c FROM Checkin c WHERE c.checkout = true")
    List<Checkin> buscarTodosOsHospedesQueJaFizeramCheckout();

    @Query("SELECT c FROM Checkin c where c.hospede.id = ?1 and c.checkout = TRUE ORDER BY c.id DESC")
    List<Checkin> buscarUltimoValorTotalHospede(Integer idHospede);

}
