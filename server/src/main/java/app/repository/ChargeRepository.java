package app.repository;

import app.entity.Charge;
import app.entity.ExpenseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChargeRepository extends JpaRepository<Charge, Integer> {
    @Query("select c from Charge c where c.expanseItem.id = ?1")
    List<Charge> findByExpanseItemsId(Integer id);

    @Query("select c from Charge c where c.expanseItem.name = ?1")
    List<Charge> findByExpanseItemName(String name);

    @Transactional
    @Modifying
    @Query("delete from Charge c where c.id = ?1")
    void deleteChargeById(Integer id);

    @Transactional
    @Modifying
    @Query("update Charge c set c.amount = ?1, c.chargeDate = ?2, c.expanseItem.id = ?3 where c.id = ?4")
    void update(@NonNull BigDecimal amount, @NonNull LocalDate chargeDate, @NonNull Integer id, Integer id1);

    @Query("select c from Charge c where c.expanseItem.id = ?1")
    Optional<ExpenseItem> findExItemById(Integer id);

    @Query("select (count(c) > 0) from Charge c where c.expanseItem.name = ?1")
    boolean isExpItemNameExists(String name);

    @Query("select (count(c) > 0) from Charge c where c.expanseItem.id = ?1")
    boolean isExpItemIdExists(Integer id);

    @Query("select (count(c) > 0) from Charge c where c.id = ?1")
    boolean idIsExists(Integer id);

    @Query("select c from Charge c where c.id = ?1")
    Optional<Charge> findChargeById(Integer id);
    //2

    @Query("select c from Charge c where c.amount between ?1 and ?2")
    List<Charge> findByAmountBetween(BigDecimal amountStart, BigDecimal amountEnd);

    @Query("select c from Charge c where c.chargeDate between ?1 and ?2")
    List<Charge> findByDateBetween(LocalDate chargeDateStart, LocalDate chargeDateEnd);
}