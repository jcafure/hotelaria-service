package br.com.senior.desafio.repository;

import br.com.senior.desafio.model.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Integer> {
}
