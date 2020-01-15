package br.com.senior.desafio.repository;

import br.com.senior.desafio.model.Hospede;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

@Service
public interface HospedeRepository extends PagingAndSortingRepository<Hospede, Integer> {

}
