package ru.phil_it.tender.dev_2.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by gennady on 28/11/18.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Balance implements Serializable{
    private Integer cardNumber;
    private Long balance;
}
