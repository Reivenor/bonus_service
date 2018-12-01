package ru.phil_it.tender.dev_2.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
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
    @Column(name = "bill_id")
    private Integer  Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    private Long sum;

    @JsonIgnore
    @ElementCollection
    @CollectionTable(
            name = "bill_positions",
            joinColumns = @JoinColumn(name = "bill_id", referencedColumnName = "bill_id")
    )
    private List<BillPosition> positions;

}
