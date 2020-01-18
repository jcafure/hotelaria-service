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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Optional<Historico> historicoHospede = historicoRepository.getHistoricoByHospede(checkin.getHospede().getId());
        if (historicoHospede.isPresent()) {
            historicoHospede.get().setValorTotal(getValorTotalDeHospedagem(checkin));
            historicoRepository.save(historicoHospede.get());
        } else {
            historicoRepository.save(Historico.builder().valorTotal(getValorTotalDeHospedagem(checkin)).hospede(checkin.getHospede()).build());
        }


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
    public List<Checkin> buscarTodosOsHospedesQueNaoFizeramCheckout() {
        return checkinRepository.buscarTodosOsHospedesQueNaoFizeramCheckout();
    }

    @Override
    public List<Checkin> buscarTodosOsHospedesQueJaFizeramCheckout() {
        List<Checkin> hospedesComCheckinECheckout = checkinRepository.buscarTodosOsHospedesQueJaFizeramCheckout();
        if (!hospedesComCheckinECheckout.isEmpty()) {
            return checkinRepository.buscarTodosOsHospedesQueJaFizeramCheckout();
        }else {
            throw new CheckinException("Não há dados para essa buscas");
        }

    }

    private Checkin buildCheckin(Checkin checkin) {

        Optional<Hospede> hospedeOptional = hospedeService.recuperaHospedePeloId(checkin.getHospede().getId());
        if (hospedeOptional.isPresent()) {
            checkin.setHospede(hospedeOptional.get());
        } else {
            throw new HospedeException(checkin.getHospede().getId());
        }
        checkin.setAdicionaVeiculo(checkin.isAdicionaVeiculo());
        checkin.setDataCheckin(checkin.getDataCheckin());
        if (checkin.getDataCheckout() != null) {
            checkin.setValorTotal(calcularValor(checkin));
        }
        checkin.setCheckout(false);

        return checkin;
    }

    private Double calcularValor(Checkin checkin) {
        LocalDateTime dataInicio = checkin.getDataCheckin();
        LocalDateTime dataFim = checkin.getDataCheckout();
        List<LocalDateTime> diasHospedado = new ArrayList<>();
        diasHospedado.add(dataInicio);
        while (dataInicio.isBefore(dataFim)) {
            diasHospedado.add(dataInicio.plusDays(1));
            dataInicio = dataInicio.plusDays(1);
        }
        Double valorFimDeSemana = valorDiariasFimDeSemana(diasHospedado, dataFim, checkin.isAdicionaVeiculo());
        Double valorDeSemana = valorDiariasDaSemana(diasHospedado, dataFim, checkin.isAdicionaVeiculo());

        return valorFimDeSemana + valorDeSemana;
    }

    private Double valorDiariasFimDeSemana(List<LocalDateTime> diariasHospedagem, LocalDateTime dataFim, boolean adicionalVeiculo) {

        int quantidadeFimSemana = diariasHospedagem.stream().filter(dia -> dia.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                || dia.getDayOfWeek().equals(DayOfWeek.SUNDAY)).collect(Collectors.toList()).size();

        boolean ultimoDiaDaDiaria = dataFim.toLocalTime().isAfter(LocalTime.parse("16:30:00"));
        quantidadeFimSemana = ultimoDiaDaDiaria ? quantidadeFimSemana + 1 : quantidadeFimSemana;
        Double valorFimDeSemana = new Double(quantidadeFimSemana * (adicionalVeiculo ? 150 + 20 : 150));
        return valorFimDeSemana;
    }

    private Double valorDiariasDaSemana(List<LocalDateTime> diariasHospedagem, LocalDateTime dataFim, boolean adicionalVeiculo) {

        int quantidadeDiaDeSemana = diariasHospedagem.stream().filter(dia -> !DayOfWeek.SATURDAY.equals(dia.getDayOfWeek())
                || DayOfWeek.SUNDAY.equals(dia.getDayOfWeek())).collect(Collectors.toList()).size();

        boolean ultimoDiaDiaria = dataFim.toLocalTime().isAfter(LocalTime.parse("16:30"));
        quantidadeDiaDeSemana = ultimoDiaDiaria ? quantidadeDiaDeSemana + 1 : quantidadeDiaDeSemana;
        Double valorDiasDaSemana = new Double(quantidadeDiaDeSemana * (adicionalVeiculo ? 120 + 15 : 120));
        return valorDiasDaSemana;

    }

    @Override
    public Double ultimoValorGastoPeloHospede(Integer idHospede) {

        double ultimoValor = 0.0;
        List<Checkin> checkins = checkinRepository.buscarUltimoValorTotalHospede(idHospede);
        Checkin checkin = checkins.get(0);
        return ultimoValor = checkin.getValorTotal();

    }

  }
