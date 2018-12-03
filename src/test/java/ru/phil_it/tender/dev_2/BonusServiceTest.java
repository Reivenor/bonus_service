package ru.phil_it.tender.dev_2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.phil_it.tender.dev_2.domain.BillRepository;
import ru.phil_it.tender.dev_2.domain.ClientRepository;
import ru.phil_it.tender.dev_2.domain.dto.NewBill;
import ru.phil_it.tender.dev_2.domain.models.Client;
import ru.phil_it.tender.dev_2.service.impl.BonusControlService;
import ru.phil_it.tender.dev_2.service.impl.exceptions.CardNumberNotFound;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertThat;
/**
 * Created by gennady on 03/12/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BonusServiceTest {


    @Mock
    private ClientRepository clientRepositoryMock;
    @Mock
    private BillRepository billRepositoryMock;

    @InjectMocks
    private BonusControlService service;

    @Before
    public void setup(){
        Client dummyClient = new Client(1, 200L);

        when(clientRepositoryMock.findById(anyInt()))
                .thenReturn(Optional.ofNullable(dummyClient));

        when(clientRepositoryMock.getOne(anyInt()))
                .thenReturn(dummyClient);

        MockitoAnnotations.initMocks(this);

        //service = new BonusControlService(billRepositoryMock, clientRepositoryMock);
    }

    @Test
    public void  addingPointTest() throws CardNumberNotFound {


        NewBill input = new NewBill(
                1,
                100,
                500L,
                Arrays.asList(
                        100L,
                        100L,
                        200L,
                        100L
                )
        );

        Client result =service.correctBonusPoints(input);
        Client expected = new Client(1, 220L);
        assertEquals(expected.getBalance(), result.getBalance());
    }

    @Test
    public void correctingPointsTest(){

    }
}
