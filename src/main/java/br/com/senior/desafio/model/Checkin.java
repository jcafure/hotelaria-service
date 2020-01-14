package br.com.senior.desafio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Checkin extends BaseEntity {

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEntrada;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSaida;

    private double valorDiaria;

    private double valorTotal;

    private double valorDaUltimaHospedagem;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hospede_id")
    private Hospede hospede;

    private boolean adicionaVeiculo;

}
