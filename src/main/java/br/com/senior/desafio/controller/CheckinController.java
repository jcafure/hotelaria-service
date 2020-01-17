package br.com.senior.desafio.controller;

import br.com.senior.desafio.exception.CheckinException;
import br.com.senior.desafio.model.Checkin;
import br.com.senior.desafio.service.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/checkins")
public class CheckinController {

    private final CheckinService checkinService;

    @Autowired
    public CheckinController(CheckinService checkinService) {
        this.checkinService = checkinService;
    }

    @PostMapping("/checkin-entrada")
    public ResponseEntity<?> checkin(@RequestBody Checkin checkin) {
        try {
            Optional<Checkin> checkinResponse = checkinService.checkinEntrada(checkin);
            return ResponseEntity.ok(checkinResponse.get());
        }catch (CheckinException e) {
            throw new CheckinException(e.getMessage());
        }catch (Exception e) {
            return new ResponseEntity<>("Houve um erro ao persistir os dados. ", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> checkinPeloId(@PathVariable("id") Integer id) {
        Optional<Checkin> optionalBebida = checkinService.getCheckinPeloId(id);
        return ResponseEntity.ok(optionalBebida.isPresent() ? optionalBebida.get() : "Checkin não encontrado! " );
    }

    @PutMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody Checkin checkin) {
        try {
            Optional<Checkin> checkinResponse = checkinService.checkinSaida(checkin);
            return ResponseEntity.ok(checkinResponse.get());
        }catch (CheckinException e) {
            throw new CheckinException(e.getMessage());
        }catch (Exception e) {
            return new ResponseEntity<>("Houve um erro ao persistir os dados. ", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/todos-hospedados")
    public ResponseEntity<?> todosHospedados() {
        try {
            List<Checkin> hospedadosPresentes = checkinService.buscarTodosOsHospedesComCheckoutFalse();
            return ResponseEntity.ok(hospedadosPresentes);
        }catch (CheckinException e) {
            throw new CheckinException(e.getMessage());
        }catch (Exception e) {
            return new ResponseEntity<>("Houve um erro na consulta dos hospedes que ainda não fizeram checkout. ", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/todos-hospedes-checkout")
    public ResponseEntity<?> todosHospedesCheckout() {
        try {
            List<Checkin> jaForamHospedados = checkinService.buscarTodosOsHospedesComCheckoutTrue();
            return ResponseEntity.ok(jaForamHospedados);
        }catch (CheckinException e) {
            throw new CheckinException(e.getMessage());
        }catch (Exception e) {
            return new ResponseEntity<>("Houve um erro na consulta dos hospedes que já fizeram checkout. ", HttpStatus.BAD_REQUEST);
        }
    }
}
