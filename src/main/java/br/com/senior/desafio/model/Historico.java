package br.com.senior.desafio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Historico implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne
    private Hospede hospede;
    @Column(name = "valorTotal")
    private double valorTotal;

}
