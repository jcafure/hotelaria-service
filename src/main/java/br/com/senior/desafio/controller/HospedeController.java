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
@RequestMapping("/hospedes")
public class HospedeController {

    private final HospedeService hospedeService;

    @Autowired
    public HospedeController(HospedeService hospedeService) {
        this.hospedeService = hospedeService;
    }

    @PostMapping("/novo-hospede")
    public ResponseEntity<?> novoHospede (@RequestBody Hospede hospede) {
        try {
            Optional<Hospede> novoHospede = hospedeService.salvarHospede(hospede);
            return ResponseEntity.ok(novoHospede.get());
        }catch (HospedeException e) {
            throw new HospedeException(e.getMessage());
        }catch (Exception e) {
            return new ResponseEntity<>("Houve um erro ao persistir os dados. ", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> hospedePeloid (@PathVariable("id") Integer id) {
        Optional<Hospede> optionalBebida = hospedeService.recuperaHospedePeloId(id);
        return ResponseEntity.ok(optionalBebida.isPresent() ? optionalBebida.get() : "Hospede não encontrado! " );
    }

    @PutMapping("/atualiza-hospede")
    public ResponseEntity<?> atualizaHospede (@RequestBody Hospede hospede) {
       try{
           Optional<Hospede> hospedeOptionalAtual = hospedeService.atualizarHospede(hospede);
           return ResponseEntity.ok(hospedeOptionalAtual.get());

        }catch (HospedeException e) {
            throw new HospedeException(e.getMessage());
        }
        catch (Exception e) {
            return new ResponseEntity<>("Houve um erro ao persistir os dados. ", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/deletar-hospede/{id}")
    public ResponseEntity<?> deletarHospede(@PathVariable("id") Integer id){
        try{
            hospedeService.deletarHospede(id);
            return ResponseEntity.ok().build();
        } catch (HospedeException e) {
            throw new HospedeException(id);
        } catch (Exception e) {
            return new ResponseEntity<>("Houve um erro na busca dos dados. ", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listar-hospedes")
    public ResponseEntity<?> listarHospedes() {
        return ResponseEntity.ok(hospedeService.listarHospedes());
    }

}
