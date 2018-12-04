package ru.phil_it.tender.dev_2.web;

import java.io.Serializable;
import java.util.List;

public class NewBillDto implements Serializable{
    private Integer cardId;
    private Integer billId;
    private Long sum;
    private List<Long> positions;

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public List<Long> getPositions() {
        return positions;
    }

    public void setPositions(List<Long> positions) {
        this.positions = positions;
    }
}
