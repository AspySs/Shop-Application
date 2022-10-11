package app.repository;

import app.entity.Sale;
import app.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
    @Transactional
    @Modifying
    @Query("update Sale s set s.quantity = ?1, s.amount = ?2, s.saleDate = ?3, s.warehouse.id = ?4 where s.id = ?5")
    int updateQuantityAndAmountAndSaleDateAndWarehouseIdByIdEquals(@NonNull BigDecimal quantity, @NonNull BigDecimal amount, @NonNull LocalDate saleDate, @NonNull Integer id, Integer id1);

    @Query("select s from Sale s where s.saleDate = ?1")
    List<Sale> findBySaleDateEquals(LocalDate saleDate);

    @Query("select s from Sale s where s.amount between ?1 and ?2")
    List<Sale> findByAmountIsBetween(BigDecimal amountStart, BigDecimal amountEnd);

    @Query("select s from Sale s where s.quantity between ?1 and ?2")
    List<Sale> findByQuantityIsBetween(BigDecimal quantityStart, BigDecimal quantityEnd);

    @Query("select (count(s) > 0) from Sale s where s.warehouse.id = ?1")
    boolean existsByWarehouse_IdEquals(Integer id);

    @Query("select (count(s) > 0) from Sale s where s.warehouse.name = ?1")
    boolean existsByWarehouse_NameEquals(String name);

    @Query("select s from Sale s where s.warehouse.id = ?1")
    Optional<Warehouse> findWareById(Integer id);

    @Query("select (count(s) > 0) from Sale s where s.id = ?1")
    boolean isExistsId(Integer id);

    @Query("select s from Sale s where s.id = ?1")
    Optional<Sale> findSaleById(Integer id);

    @Query("select s from Sale s where s.warehouse.name = ?1")
        //2
    List<Sale> findSalesByWarehouseName(String name);

    @Query("select s from Sale s where s.warehouse.id = ?1")
        //2
    List<Sale> findSalesByWarehouseId(Integer id);


    @Override
    <S extends Sale> S save(S s);
}