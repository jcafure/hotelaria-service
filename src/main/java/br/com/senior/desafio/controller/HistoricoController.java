package br.com.senior.desafio.controller;

import br.com.senior.desafio.exception.HistoricoException;
import br.com.senior.desafio.service.HistoricoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/historicos")
public class HistoricoController {

    private final HistoricoService historicoService;

    @Autowired
    public HistoricoController(HistoricoService historicoService) {
        this.historicoService = historicoService;
    }

    @ApiOperation("valor total já gasto pelo hóspede no hotel. ")
    @GetMapping("/busca-valor-total/{idHospede}")
    public ResponseEntity<?> checkinPeloId(@PathVariable("idHospede") Integer idHospede) {
        try {
            Double valorTotal = historicoService.getValorTotalJaGastoPeloHospede(idHospede);
            return ResponseEntity.ok(valorTotal);
        }catch (HistoricoException e) {
            throw new HistoricoException(e.getMessage());
        }
    }

}
