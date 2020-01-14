package br.com.senior.desafio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Hospede extends BaseEntity {

    private String nome;
    private String documento;
    private String telefone;

}
