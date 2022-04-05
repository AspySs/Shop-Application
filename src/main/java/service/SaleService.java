package service;

import entity.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.SaleRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private final SaleRepository repository;

    @Autowired
    public SaleService(SaleRepository repository){this.repository = repository;}

    public void deleteById(Integer id){
        repository.deleteById(id);
    }

    public Optional<Sale> findById(Integer id){
        return repository.findSaleById(id);
    }

    public Sale save(Sale sale){
        return repository.save(sale);
    }

    public Iterable<Sale> findAll(){
        return repository.findAll();
    }

    public List<Sale> findByWarehouseId(Integer id){
        return repository.findSalesByWarehouseId(id);
    }

    public List<Sale> findByWarehouseName(String name){
        return repository.findSalesByWarehouseName(name);
    }

    public long countInTime(LocalDate saleDateStart, LocalDate saleDateEnd){
        return repository.countSoldItemsInTime(saleDateStart, saleDateEnd);
    }

    public List<Sale> findSalesWithGreaterAmountWarehouse(BigDecimal amount){
        return repository.findSalesWhereWarehouseAmountGreater(amount);
    }

}
