package br.com.senior.desafio.controller;

import br.com.senior.desafio.exception.CheckinException;
import br.com.senior.desafio.model.Checkin;
import br.com.senior.desafio.service.CheckinService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("Permiti fazer o checkin. Caso a data checkout estiver preenchida é gerado o cálculo.")
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

    @ApiOperation("No checkout será gerado o valor total da hospedagem. Quando a data de saida é preenchida o service fara o cálculo da hospedagem.")
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

    @ApiOperation("Consultar hóspedes que ainda estão no hotel.")
    @GetMapping("/todos-hospedados")
    public ResponseEntity<?> todosHospedados() {
        try {
            List<Checkin> hospedadosPresentes = checkinService.buscarTodosOsHospedesQueNaoFizeramCheckout();
            return ResponseEntity.ok(hospedadosPresentes);
        }catch (CheckinException e) {
            throw new CheckinException(e.getMessage());
        }catch (Exception e) {
            return new ResponseEntity<>("Houve um erro na consulta dos hospedes que ainda não fizeram checkout. ", HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation("Consultar hóspedes que já realizaram o check in e não estão mais no hotel.")
    @GetMapping("/todos-hospedes-checkout")
    public ResponseEntity<?> todosHospedesCheckout() {
        try {
            List<Checkin> jaForamHospedados = checkinService.buscarTodosOsHospedesQueJaFizeramCheckout();
            return ResponseEntity.ok(jaForamHospedados);
        }catch (CheckinException e) {
            throw new CheckinException(e.getMessage());
        }catch (Exception e) {
            return new ResponseEntity<>("Houve um erro na consulta dos hospedes que já fizeram checkout. ", HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation("valor da última hospedagem já gasto pelo hóspede no hotel.")
    @GetMapping("/ultimo-valor-hospede/{id}")
    public ResponseEntity<?> ultimoValorHospede(@PathVariable("id") Integer id) {
        try {
           Double ultimoValor = checkinService.ultimoValorGastoPeloHospede(id);
            return ResponseEntity.ok(ultimoValor);
        }catch (CheckinException e) {
            throw new CheckinException(e.getMessage());
        }catch (Exception e) {
            return new ResponseEntity<>("Houve um erro na consulta dos hospedes que já fizeram checkout. ", HttpStatus.BAD_REQUEST);
        }
    }
}
