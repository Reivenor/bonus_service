package ru.phil_it.tender.dev_2.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gennady on 28/11/18.
 */
@Data
@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    private Integer Id;

    private Long balance;

    public Client(Integer id, Long balance){
        this.setId(id);
        this.setBalance(balance);
        this.setBills(new ArrayList<>());
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    //@JoinColumn(name = "bill_id", nullable = false)
    @JsonIgnore
    private List<Bill> bills;
}
