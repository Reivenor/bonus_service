package ru.phil_it.tender.dev_2.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by gennady on 28/11/18.
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillPosition implements Serializable{


    private Long sum;
}
