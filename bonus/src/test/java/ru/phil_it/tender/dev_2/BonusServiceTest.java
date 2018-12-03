package ru.phil_it.tender.dev_2;

import net.bytebuddy.asm.Advice;
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
import ru.phil_it.tender.dev_2.service.impl.exceptions.BillAlreadyExists;
import ru.phil_it.tender.dev_2.service.impl.exceptions.BillSum;
import ru.phil_it.tender.dev_2.service.impl.exceptions.CardNumberNotFound;
import ru.phil_it.tender.dev_2.service.impl.exceptions.NegativeBalance;

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
        Client badClient = new Client(2, -200L);

        when(clientRepositoryMock.findById(1))
                .thenReturn(Optional.ofNullable(dummyClient));

        when(clientRepositoryMock.getOne(1))
                .thenReturn(dummyClient);

        when(clientRepositoryMock.findById(2))
                .thenReturn(Optional.ofNullable(badClient));

        when(clientRepositoryMock.getOne(2))
                .thenReturn(badClient);

        MockitoAnnotations.initMocks(this);

        //service = new BonusControlService(billRepositoryMock, clientRepositoryMock);
    }

    @Test
    public void  addingPointTest() throws CardNumberNotFound, NegativeBalance, BillSum, BillAlreadyExists {


        NewBill input = new NewBill(
                1,
                100,
                400L,
                Arrays.asList(
                        100L,
                        100L,
                        200L,
                        100L
                )
        );

        Client result =service.correctBonusPoints(input);
        Client expected = new Client(1, 190L);
        assertEquals(expected.getBalance(), result.getBalance());
    }

    @Test(expected = NegativeBalance.class)
    public void negativeBalance() throws BillSum, CardNumberNotFound, NegativeBalance, BillAlreadyExists {
        NewBill input = new NewBill(
                2,
                100,
                500L,
                Arrays.asList(
                        100L,
                        100L,
                        200L,
                        100L
                )
        );
        service.correctBonusPoints(input);
    }

    @Test(expected = CardNumberNotFound.class)
    public void negativeCardNumberNotFound() throws BillSum, CardNumberNotFound, NegativeBalance, BillAlreadyExists {
        NewBill input = new NewBill(
                10,
                100,
                500L,
                Arrays.asList(
                        100L,
                        100L,
                        200L,
                        100L
                )
        );
        service.correctBonusPoints(input);
    }

    @Test(expected = BillAlreadyExists.class)
    public void negativeBillAlreadyExists() throws BillSum, CardNumberNotFound, NegativeBalance, BillAlreadyExists {
        NewBill input1 = new NewBill(
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

        NewBill input2 = new NewBill(
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
        service.correctBonusPoints(input1);
        service.correctBonusPoints(input2);
    }

    @Test
    public void correctingPointsTest(){

    }
}
