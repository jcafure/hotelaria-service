package br.com.senior.desafio.repository;

import br.com.senior.desafio.model.Hospede;
import br.com.senior.desafio.utils.MockUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class HospedeRepositoryTest {

    @Mock
    private HospedeRepository hospedeRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void save() {
        Hospede hospede =  MockUtils.buildHospede(1, "Cliente do Hotel", "36651547", "5987651");
        when(hospedeRepository.save(hospede)).thenReturn(hospede);
    }
}
