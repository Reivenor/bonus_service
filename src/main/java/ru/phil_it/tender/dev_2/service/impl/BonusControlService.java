package ru.phil_it.tender.dev_2.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.phil_it.tender.dev_2.domain.BillRepository;
import ru.phil_it.tender.dev_2.domain.ClientRepository;
import ru.phil_it.tender.dev_2.domain.dto.Balance;
import ru.phil_it.tender.dev_2.domain.dto.NewBill;
import ru.phil_it.tender.dev_2.domain.models.Bill;
import ru.phil_it.tender.dev_2.domain.models.BillPosition;
import ru.phil_it.tender.dev_2.domain.models.Client;
import ru.phil_it.tender.dev_2.service.impl.exceptions.CardNumberNotFound;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Created by gennady on 28/11/18.
 */
@Slf4j
@Service
public class BonusControlService {
    private final BillRepository billRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public BonusControlService(BillRepository billRepository,
                               ClientRepository clientRepository) {
        this.billRepository = billRepository;
        this.clientRepository = clientRepository;
    }

    public void correctBonusPoints(NewBill newBill) throws CardNumberNotFound {
        AtomicLong billPositionsSum = new AtomicLong(0L);

        Client client =  clientRepository.findById(newBill.getCardId())
                .orElseThrow(() -> new CardNumberNotFound(newBill.getCardId(), log));

        Bill billToSave = Bill.builder()
                .billId(newBill.getBillId())
                .cardId(newBill.getCardId())
                .sum(newBill.getSum())
                .positions(newBill.getPositions()
                        .stream()
                        .map(value -> {
                            billPositionsSum.addAndGet(value);
                            return new BillPosition(newBill.getBillId(), value);
                        }).collect(Collectors.toList()))
                .build();

        billRepository.save(billToSave);
        log.info("New bill saved");
        //TODO experimental
        Long pointsToRemove = billPositionsSum.get() - billToSave.getSum() / 10;
        log.info("Removed points count " + pointsToRemove);
        client.setBalance(client.getBalance() - pointsToRemove);
        log.info("Current client balance " + client.getBalance());
        client.setBalance(client.getBalance() + computeBonusPointsBySum(sumClientBills(client)));
        log.info("Client " + client.getCardId() + " balance " + client.getBalance());
        clientRepository.save(client);

    }


    public Balance getCardBalance(Integer cardId) throws CardNumberNotFound {
        Client client =  clientRepository.findById(cardId)
                .orElseThrow(() -> new CardNumberNotFound(cardId, log));
        return new Balance(cardId, client.getBalance());
    }

    /** Переводит сумму чеков в деньгах в бонусные баллы по правилам начисления
     *
     * @param allClientBillSum сумма всех чеков
     * @return сумма баллов для начисления???
     */
    private Long computeBonusPointsBySum(Long allClientBillSum){
        Long pointsSum = 0L;

        if(allClientBillSum <= 50_000){
            pointsSum = allClientBillSum / 50;
        } else if(allClientBillSum > 50_000 && allClientBillSum <= 100_000){
            pointsSum = allClientBillSum / 40;
        } else if(allClientBillSum > 100_000){
            pointsSum = allClientBillSum / 30;
        }

        return pointsSum;
    }

    private Long convertBonusPointsToMoney(Long points) {
        return points * 10;
    }

    private Long sumClientBills(Client client){
        return client.getBills()
                .stream()
                .map(Bill::getSum)
                .reduce((billSum, accumulator) -> accumulator + billSum)
                .orElseGet(() -> 0L);
    }


}
