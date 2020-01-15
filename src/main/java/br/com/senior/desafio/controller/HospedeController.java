package br.com.senior.desafio.controller;

import br.com.senior.desafio.exception.HospedeException;
import br.com.senior.desafio.model.Hospede;
import br.com.senior.desafio.service.HospedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/hospede")
public class HospedeController {

    private final HospedeService hospedeService;

    @Autowired
    public HospedeController(HospedeService hospedeService) {
        this.hospedeService = hospedeService;
    }

    @PostMapping("/novo-hospede")
    public ResponseEntity<?> novoHospede (@RequestBody Hospede hospede) {
        try {
            Optional<Hospede> novoHospede = hospedeService.save(hospede);
            return ResponseEntity.ok(novoHospede.get());
        }catch (HospedeException e) {
            throw new HospedeException(e.getMessage());
        }catch (Exception e) {
            return new ResponseEntity<>("Houve um erro ao persistir os dados. ", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/atualiza-hospede")
    public ResponseEntity<?> atualizaHospede (@RequestBody Hospede hospede) {
       try{
           Optional<Hospede> hospedeOptionalAtual = hospedeService.update(hospede);
           return ResponseEntity.ok(hospedeOptionalAtual.get());

        }catch (HospedeException e) {
            throw new HospedeException(e.getMessage());
        }
        catch (Exception e) {
            return new ResponseEntity<>("Houve um erro ao persistir os dados. ", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getModel() {
        Hospede hospedeToJson = new Hospede();
        return ResponseEntity.ok(hospedeToJson);
    }

}
