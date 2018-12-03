package ru.phil_it.tender.dev_2.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.phil_it.tender.dev_2.domain.BillRepository;
import ru.phil_it.tender.dev_2.domain.ClientRepository;
import ru.phil_it.tender.dev_2.domain.dto.Balance;
import ru.phil_it.tender.dev_2.domain.dto.NewBill;
import ru.phil_it.tender.dev_2.domain.models.Bill;
import ru.phil_it.tender.dev_2.domain.models.BillPosition;
import ru.phil_it.tender.dev_2.domain.models.Client;
import ru.phil_it.tender.dev_2.service.impl.exceptions.BillAlreadyExists;
import ru.phil_it.tender.dev_2.service.impl.exceptions.BillSum;
import ru.phil_it.tender.dev_2.service.impl.exceptions.CardNumberNotFound;
import ru.phil_it.tender.dev_2.service.impl.exceptions.NegativeBalance;

import java.util.Collection;
import java.util.List;
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

    @Transactional(readOnly = true)
    public Client correctBonusPoints(NewBill newBill) throws CardNumberNotFound, NegativeBalance, BillAlreadyExists, BillSum {
        AtomicLong billPositionsSum = new AtomicLong(0L);

        if (billRepository.findById(newBill.getBillId()).isPresent())
            throw new BillAlreadyExists(newBill.getBillId(), log);

        Client client =  clientRepository.findById(newBill.getCardId())
                .orElseThrow(() -> new CardNumberNotFound(newBill.getCardId(), log));
        //log.info("Current client stats " + client.getId() + ": balance " + client.getBalance());
        Bill billToSave = Bill.builder()
                .Id(newBill.getBillId())
                .client(clientRepository.getOne(newBill.getCardId()))
                .sum(newBill.getSum())
                .positions(newBill.getPositions()
                        .stream()
                        .map(value -> {
                            billPositionsSum.addAndGet(value);
                            return new BillPosition(value);
                        }).collect(Collectors.toList()))
                .build();

        //TODO experimental
        long pointsToRemove = (billPositionsSum.get() - billToSave.getSum()) / 10;
        if (pointsToRemove < 0) throw new BillSum(newBill.getCardId(), log);

        Long newClientPoints = client.getBalance() - pointsToRemove;
         log.info("Removed points count " + pointsToRemove);
        if (newClientPoints < 0) throw new NegativeBalance(newBill.getCardId(), log);

        billRepository.save(billToSave);
        log.info("New bill saved");
        log.info("Current client balance " + client.getBalance());

        client = clientRepository.findById(newBill.getCardId())
                .orElseThrow(() -> new CardNumberNotFound(newBill.getCardId(), log));

        long totalClientSum = sumClientBills(client);
        long totalClientPositionsSum = sumClientBillsSum(client);
        long totalRemovedPoints = totalClientSum - totalClientPositionsSum / 10;
        long totalClientPoints = computeBonusPointsBySum(totalClientSum);
        Long ResultPoints = totalClientPoints - totalRemovedPoints;

        log.info("Client total sum " + totalClientSum);
        log.info("Client total sum of positions " + totalClientPositionsSum);
        log.info("Client total points " + totalClientPositionsSum);
        log.info("Client total removed points " + totalClientPositionsSum);

        client.setBalance(ResultPoints);
        log.info("Client " + client.getId() + " balance " + client.getBalance());

        billRepository.save(billToSave);
        log.info("New bill saved");

        clientRepository.save(client);

        return client;
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

    private Long sumClientBillsSum(Client client){
        return client.getBills()
                .stream()
                .map(Bill::getPositions)
                .flatMap(Collection::stream)
                .map(BillPosition::getSum)
                .reduce((billSum, accumulator) -> accumulator + billSum)
                .orElseGet(() -> 0L);
    }


}
