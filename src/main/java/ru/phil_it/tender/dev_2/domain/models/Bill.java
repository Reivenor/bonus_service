package ru.phil_it.tender.dev_2.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by gennady on 28/11/18.
 */
@Data
@Entity
@Table(name = "bill")
@Builder
public class Bill {
    @Id
    private Integer  billId;

    private Integer cardId;

    private Long sum;

    @JsonIgnore
    @ElementCollection
    private List<BillPosition> positions;

}
