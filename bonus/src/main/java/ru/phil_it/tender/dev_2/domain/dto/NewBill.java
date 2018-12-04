package ru.phil_it.tender.dev_2.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.phil_it.tender.dev_2.service.impl.ListElementsValidation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by gennady on 28/11/18.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBill implements Serializable{
    @NotNull(message = "Bill must contain client card ID")
    private Integer cardId;
    @NotNull(message = "Bill must contain billID")
    private Integer billId;
    @Min(value = 0L, message = "Sum must be greater then zero.")
    private Long sum;
    @ListElementsValidation
    private List<Long> positions;
}
