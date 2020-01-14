package br.com.senior.desafio.repository;

import br.com.senior.desafio.model.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface HospedeRepository extends JpaRepository<Hospede, Integer> {


}
