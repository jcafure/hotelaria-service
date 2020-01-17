package br.com.senior.desafio.service.serviceimp;

import br.com.senior.desafio.exception.CheckinException;
import br.com.senior.desafio.exception.HospedeException;
import br.com.senior.desafio.model.Checkin;
import br.com.senior.desafio.model.Historico;
import br.com.senior.desafio.model.Hospede;
import br.com.senior.desafio.repository.CheckinRepository;
import br.com.senior.desafio.repository.HistoricoRepository;
import br.com.senior.desafio.service.CheckinService;
import br.com.senior.desafio.service.HospedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CheckinServiceImp implements CheckinService {

    private final CheckinRepository checkinRepository;
    private final HospedeService hospedeService;
    private final HistoricoRepository historicoRepository;

    @Autowired
    public CheckinServiceImp(CheckinRepository checkinRepository, HospedeService hospedeService, HistoricoRepository historicoRepository) {
        this.checkinRepository = checkinRepository;
        this.hospedeService = hospedeService;
        this.historicoRepository = historicoRepository;
    }

    @Override
    public Optional<Checkin> checkinEntrada(Checkin checkin) {
        return Optional.of(checkinRepository.save(buildCheckin(checkin)));
    }

    @Override
    public Optional<Checkin> checkinSaida(Checkin checkin) {
        Optional<Checkin> checkout = getCheckinPeloId(checkin.getId());
            if (checkout.isPresent()) {
                buildCheckout(checkout.get());
            } else {
                throw new CheckinException("Não foi encontrado nenhuma reserva");
            }
        return Optional.of(checkout.get());
    }

    private void buildHistorico(Checkin checkin) {
       historicoRepository.save(Historico.builder().valorTotal(getValorTotalDeHospedagem(checkin)).hospede(checkin.getHospede()).build());

    }

    private double getValorTotalDeHospedagem(Checkin checkin) {
        return checkinRepository.valorTotalDeHospedagensJaFeitas(checkin.getHospede());
    }

    private void buildCheckout(Checkin checkin) {
        checkin.setDataCheckout(checkin.getDataCheckout());
        checkin.setAdicionaVeiculo(checkin.isAdicionaVeiculo());
        checkin.setValorTotal(calcularValor(checkin));
        checkin.setCheckout(true);
        checkinRepository.save(checkin);
        buildHistorico(checkin);
    }

    @Override
    public Optional<Checkin> getCheckinPeloId(Integer id) {
        return checkinRepository.findById(id);
    }

    @Override
    public List<Checkin> buscarTodosHospedados() {
        return checkinRepository.buscarTodosHospedados();
    }

    private Checkin buildCheckin(Checkin checkin) {

        Optional<Hospede> hospedeOptional = hospedeService.recuperaHospedePeloId(checkin.getHospede().getId());
        if (hospedeOptional.isPresent()) {
            checkin.setHospede(hospedeOptional.get());
        } else {
            throw new HospedeException(checkin.getHospede().getId());
        }

        checkin.setDataCheckin(checkin.getDataCheckin());
        if (checkin.getDataCheckout() != null) {
            checkin.setValorTotal(calcularValor(checkin));
        }
        checkin.setAdicionaVeiculo(checkin.isAdicionaVeiculo());
        checkin.setCheckout(false);

        return checkin;
    }

    private Double calcularValor(Checkin checkin) {
        LocalDateTime dataInicio = checkin.getDataCheckin();
        LocalDateTime dataFim = checkin.getDataCheckout();
        List<LocalDateTime> diasHospedado = Stream.iterate(dataInicio, start -> start.plusDays(1)).limit(ChronoUnit.DAYS.between(dataInicio, dataFim)).collect(Collectors.toList());
        Double valorFimDeSemana = valorDiariasFimDeSemana(diasHospedado, dataFim, checkin.isAdicionaVeiculo());
        Double valorDeSemana = valorDiariasDaSemana(diasHospedado, checkin.isAdicionaVeiculo());

        return valorFimDeSemana + valorDeSemana;
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
