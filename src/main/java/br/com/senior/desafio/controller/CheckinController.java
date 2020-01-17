package br.com.senior.desafio.controller;

import br.com.senior.desafio.exception.CheckinException;
import br.com.senior.desafio.model.Checkin;
import br.com.senior.desafio.service.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> checkin (@RequestBody Checkin checkin) {
        try {
            Optional<Checkin> checkinResponse = checkinService.checkinEntrada(checkin);
            return ResponseEntity.ok(checkinResponse.get());
        }catch (CheckinException e) {
            throw new CheckinException(e.getMessage());
        }catch (Exception e) {
            return new ResponseEntity<>("Houve um erro ao persistir os dados. ", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/checkout")
    public ResponseEntity<?> checkout (@RequestBody Checkin checkin) {
        try {
            Optional<Checkin> checkinResponse = checkinService.checkinSaida(checkin);
            return ResponseEntity.ok(checkinResponse.get());
        }catch (CheckinException e) {
            throw new CheckinException(e.getMessage());
        }catch (Exception e) {
            return new ResponseEntity<>("Houve um erro ao persistir os dados. ", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getModel() {
        Checkin checinToJson = new Checkin();
        return ResponseEntity.ok(checinToJson);
    }
}
