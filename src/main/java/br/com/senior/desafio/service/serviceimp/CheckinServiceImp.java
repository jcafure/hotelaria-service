package br.com.senior.desafio.service.serviceimp;

import br.com.senior.desafio.exception.HospedeException;
import br.com.senior.desafio.model.Checkin;
import br.com.senior.desafio.model.Hospede;
import br.com.senior.desafio.repository.CheckinRepository;
import br.com.senior.desafio.service.CheckinService;
import br.com.senior.desafio.service.HospedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class CheckinServiceImp implements CheckinService {

    private final CheckinRepository checkinRepository;
    private final HospedeService hospedeService;

    @Autowired
    public CheckinServiceImp(CheckinRepository checkinRepository, HospedeService hospedeService) {
        this.checkinRepository = checkinRepository;
        this.hospedeService = hospedeService;
    }

    @Override
    public Optional<Checkin> createCheckin(Checkin checkin) {

        return Optional.of(checkinRepository.save(buildCheckin(checkin)));
    }

    private Checkin buildCheckin(Checkin checkin) {

        Optional<Hospede> hospedeOptional = hospedeService.findById(checkin.getHospede().getId());
        if (hospedeOptional.isPresent()) {
            checkin.setHospede(hospedeOptional.isPresent() ? hospedeOptional.get() : null);
        } else {
            throw new HospedeException(checkin.getHospede().getId());
        }

        checkin.setDataCheckin(checkin.getDataCheckin());
        checkin.setDataCheckout(checkin.getDataCheckout());
        checkin.setAdicionaVeiculo(checkin.isAdicionaVeiculo());

        return checkin;
    }

    private Double valorDiariasFimDeSemana(List<LocalDateTime> diariasHospedagem, LocalDateTime dataFim, boolean adicionalVeiculo) {
        Long diasFimDeSemana = diariasHospedagem.stream().filter(data -> DayOfWeek.SATURDAY.equals(data.getDayOfWeek())
                && DayOfWeek.SUNDAY.equals(data.getDayOfWeek())).count();

        boolean ultimoDiaDaDiaria = dataFim.toLocalTime().isAfter(LocalTime.parse("16:30:00"));
        diasFimDeSemana = ultimoDiaDaDiaria ? diasFimDeSemana + 1 : diasFimDeSemana;
        Double valorFimDeSemana = new Double(diasFimDeSemana * (adicionalVeiculo ? 150 + 20 : 150));
        return valorFimDeSemana;

    }


    private Double valorDiariasDaSemana(List<LocalDateTime> diariasHospedagem, boolean adicionalVeiculo) {
        Long diasDaSemana = diariasHospedagem.stream().filter(data -> !DayOfWeek.SATURDAY.equals(data.getDayOfWeek())
                && !DayOfWeek.SUNDAY.equals(data.getDayOfWeek())).count();

        Double valorDiasDaSemana = new Double(diasDaSemana * (adicionalVeiculo ? 120 + 15 : 120));
        return valorDiasDaSemana;


    }

}
