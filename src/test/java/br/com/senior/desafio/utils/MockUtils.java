package br.com.senior.desafio.utils;

import br.com.senior.desafio.model.Hospede;

import java.time.LocalDateTime;

public class MockUtils {

    public static Hospede buildHospede(Integer id, String nome, String telefone, String documento) {
        return Hospede.builder().id(id).nome(nome).telefone(telefone).documento(documento).build();
    }

    public static void main(String args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
    }
}
