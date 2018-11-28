package ru.phil_it.tender.dev_2.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gennady on 28/11/18.
 */
@Data
@NoArgsConstructor
public class NewBill implements Serializable{
    private Integer cardId;
    private Integer billId;
    private Long sum;
    private List<Long> positions;
}
