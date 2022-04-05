package repository;

import entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
    @Query("select s from Sale s where s.warehouse.amount > ?1")
    List<Sale> findSalesWhereWarehouseAmountGreater(BigDecimal amount); //2

    @Query("select s from Sale s where s.id = ?1")
    Optional<Sale> findSaleById(Integer id);

    @Query("select s from Sale s where s.warehouse.name = ?1")      //2
    List<Sale> findSalesByWarehouseName(String name);

    @Query("select s from Sale s where s.warehouse.id = ?1")        //2
    List<Sale> findSalesByWarehouseId(Integer id);

    @Query("select count(s) from Sale s where s.saleDate between ?1 and ?2")
    long countSoldItemsInTime(LocalDate saleDateStart, LocalDate saleDateEnd);

    @Override
    <S extends Sale> S save(S s);
}