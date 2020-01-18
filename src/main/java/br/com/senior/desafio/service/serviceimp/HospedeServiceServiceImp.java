package br.com.senior.desafio.service.serviceimp;

import br.com.senior.desafio.exception.HospedeException;
import br.com.senior.desafio.model.Hospede;
import br.com.senior.desafio.repository.HospedeRepository;
import br.com.senior.desafio.service.HospedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class HospedeServiceServiceImp implements HospedeService {

    private final HospedeRepository hospedeRepository;

    @Autowired
    public HospedeServiceServiceImp(HospedeRepository hospedeRepository) {
        this.hospedeRepository = hospedeRepository;
    }

    @Override
    public Optional<Hospede> recuperaHospedePeloId(Integer id) {
        return hospedeRepository.findById(id);
    }

    @Override
    public Optional<Hospede> salvarHospede(Hospede hospede) {
        validaHospede(hospede);
        return Optional.of(hospedeRepository.save(buildHospede(hospede)));
    }

    @Override
    public Optional<Hospede> atualizarHospede(Hospede hospede) {
        Optional<Hospede> optionalHospede = recuperaHospedePeloId(hospede.getId());

        if (optionalHospede.isPresent()) {
            hospedeRepository.save(buildHospede(hospede));
        } else {
            throw new HospedeException(hospede.getId());
        }
        return Optional.ofNullable(hospede);
    }

    @Override
    public void deletarHospede(Integer id) {
        hospedeRepository.deleteById(id);
    }

    @Override
    public Iterable<Hospede> listarHospedes() {
        return hospedeRepository.findAll();
    }

    private Hospede buildHospede(Hospede hospede) {

        hospede.setNome(hospede.getNome());
        hospede.setDocumento(hospede.getDocumento());
        hospede.setTelefone(hospede.getTelefone());

        return hospede;
    }

    private void validaHospede(Hospede hospede) {

        if (StringUtils.isEmpty(hospede.getNome())){
            throw new HospedeException("Nome é um campo obrigatório.");
        }
        if (StringUtils.isEmpty(hospede.getDocumento())){
            throw new HospedeException("Documento é um campo obrigatório.");
        }
        if (StringUtils.isEmpty(hospede.getTelefone())){
            throw new HospedeException("Telefone é um campo obrigatório.");
        }
    }
}
