package app.repository;

import app.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
    @Transactional
    @Modifying
    @Query("update Warehouse w set w.name = ?1, w.quantity = ?2, w.amount = ?3 where w.id = ?4")
    int warehouseUpdate(@NonNull String name, @NonNull BigDecimal quantity, @NonNull BigDecimal amount, Integer id);

    @Query("select w from Warehouse w where w.name = ?1")
    List<Warehouse> findByNameEquals(String name);

    @Query("select (count(w) > 0) from Warehouse w where w.name = ?1")
    boolean isExistsName(String name);

    @Query("select (count(w) > 0) from Warehouse w where w.id = ?1")
    boolean isExistsId(Integer id);

    @Query("select w from Warehouse w where w.id = ?1")
    Optional<Warehouse> findWarehouseById(Integer id);

    @Modifying
    @Query("delete from Warehouse w where w.name = ?1")
    void deleteByName(String name);

    @Query("select w from Warehouse w where w.quantity > ?1")
    List<Warehouse> findByQuantityGreater(BigDecimal quantity);

    @Query("select w from Warehouse w where w.amount between ?1 and ?2")
    List<Warehouse> findByAmountBetween(BigDecimal amountStart, BigDecimal amountEnd);

    @Query("select w from Warehouse w where w.quantity < ?1")
    List<Warehouse> findByQuantityLess(BigDecimal quantity);

    @Override
    <S extends Warehouse> S save(S s);
}