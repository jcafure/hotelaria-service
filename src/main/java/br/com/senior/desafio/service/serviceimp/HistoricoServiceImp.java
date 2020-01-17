package br.com.senior.desafio.service.serviceimp;

import br.com.senior.desafio.exception.HistoricoException;
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
        try {
            Optional<Historico> historicoOptional = historicoRepository.getHistoricoByHospede(idHospede);
            if (historicoOptional.isPresent()) {
                return historicoRepository.getHistoricoByHospede(idHospede);
            }
        }catch (HistoricoException e) {
            throw new HistoricoException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Double getValorTotalJaGastoPeloHospede(Integer idHospede) {
        try {
            Optional<Historico> historicoOptional = historicoRepository.getHistoricoByHospede(idHospede);
            if (historicoOptional.isPresent()) {
                return historicoRepository.getValorTotalJaGastoPeloHospede(idHospede);
            } else {
                throw new HistoricoException("Hospede Não encontrado");
            }
        }catch (HistoricoException e) {
            throw new HistoricoException(e.getMessage());
        }
    }
}
