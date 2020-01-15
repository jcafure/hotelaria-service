package br.com.senior.desafio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Historico implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "idCheckin")
    private Integer idCheckin;
    @Column(name = "valorUltimaHospedagem")
    private double valorUltimaHospedagem;
    @Column(name = "valorTotal")
    private double valorTotal;

}
