package repository;

import entity.Charge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChargeRepository extends JpaRepository<Charge, Integer> {
    @Query("select c from Charge c where c.id = ?1")
    Optional<Charge> findChargeById(Integer id);
    @Modifying
    @Query("delete from Charge c where c.id = ?1")
    int deleteChargeById(Integer id);
    @Query("select c from Charge c where c.expanseItem.name = ?1")
    Optional<Charge> findByExpanseItemName(String name);            //2
    @Query("select c from Charge c where c.expanseItem.id = ?1")
    Optional<Charge> findByExpanseItemsId(Integer id);              //2

    @Query("select c from Charge c where c.amount between ?1 and ?2")
    List<Charge> findByAmountBetween(BigDecimal amountStart, BigDecimal amountEnd);

    @Query("select c from Charge c where c.chargeDate between ?1 and ?2")
    List<Charge> findByDateBetween(LocalDate chargeDateStart, LocalDate chargeDateEnd);

    @Override
    <S extends Charge> S save(S s);
}