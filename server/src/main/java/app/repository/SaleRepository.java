package app.repository;

import app.entity.Sale;
import app.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
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

    @Query("select s from Sale s where s.warehouse.name = ?1")      //2
    List<Sale> findSalesByWarehouseName(String name);

    @Query("select s from Sale s where s.warehouse.id = ?1")        //2
    List<Sale> findSalesByWarehouseId(Integer id);

    @Query("select count(s) from Sale s where s.saleDate between ?1 and ?2")
    long countSoldItemsInTime(LocalDate saleDateStart, LocalDate saleDateEnd);

    @Query("select s from Sale s where s.warehouse.name = ?1")
    List<Sale> findSoldItemsByName(String name);                    //2

    @Override
    <S extends Sale> S save(S s);
}