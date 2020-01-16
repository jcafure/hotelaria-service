package br.com.senior.desafio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Checkin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dataEntrada", nullable = false)
    private LocalDateTime dataCheckin;

    @Column(name = "dataSaida")
    private LocalDateTime dataCheckout;

    private double valorTotal;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hospede_id")
    private Hospede hospede;

    private boolean adicionaVeiculo;

    private boolean checkout;
//
//    public boolean isAdicionaVeiculo() {
//        return adicionaVeiculo;
//    }
//
//    public void setAdicionaVeiculo(boolean adicionaVeiculo) {
//        this.adicionaVeiculo = adicionaVeiculo;
//    }
}
