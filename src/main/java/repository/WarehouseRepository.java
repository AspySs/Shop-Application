package repository;

import entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
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

    @Query("select w from Warehouse w where w.name = ?1")
    Optional<Warehouse> findByName(String name);

    @Override
    <S extends Warehouse> S save(S s);
}