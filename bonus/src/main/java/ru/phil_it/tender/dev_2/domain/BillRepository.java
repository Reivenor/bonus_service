package ru.phil_it.tender.dev_2.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.phil_it.tender.dev_2.domain.models.Bill;

/**
 * Created by gennady on 28/11/18.
 */
@Repository
public interface BillRepository extends JpaRepository<Bill, Integer>{
}
