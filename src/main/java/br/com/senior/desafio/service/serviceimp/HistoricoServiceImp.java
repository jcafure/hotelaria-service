package br.com.senior.desafio.service.serviceimp;

import br.com.senior.desafio.model.Historico;
import br.com.senior.desafio.repository.HistoricoRepository;
import br.com.senior.desafio.service.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HistoricoServiceImp implements HistoricoService {

    private final HistoricoRepository historicoRepository;

    @Autowired
    public HistoricoServiceImp(HistoricoRepository historicoRepository) {
        this.historicoRepository = historicoRepository;
    }

    @Override
    public Optional<Historico> getHistoricoPorHospede(Integer idHospede) {
        return historicoRepository.getHistoricoByHospede(idHospede);
    }
}
