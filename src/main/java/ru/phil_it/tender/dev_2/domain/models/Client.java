package ru.phil_it.tender.dev_2.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.util.List;

/**
 * Created by gennady on 28/11/18.
 */
@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    private Integer Id;

    private Long balance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    //@JoinColumn(name = "bill_id", nullable = false)
    @JsonIgnore
    private List<Bill> bills;
}
