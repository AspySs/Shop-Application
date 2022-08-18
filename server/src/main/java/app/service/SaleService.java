package app.service;

import app.entity.Sale;
import app.entity.Warehouse;
import app.exceptions.SaleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import app.repository.SaleRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private final SaleRepository repository;

    @Autowired
    public SaleService(SaleRepository repository){this.repository = repository;}

    @Transactional
    @Modifying
    public void deleteById(Integer id){
        if(!repository.isExistsId(id)){
            throw new SaleNotFoundException("Sale not found by ID");
        }
        repository.deleteById(id);
    }

    public Sale findById(Integer id){
        Optional<Sale> sale = repository.findSaleById(id);
        if(sale.isPresent()){
            return sale.get();
        }
        else {
            throw new SaleNotFoundException("Sale not found by ID");
        }
    }

    public Optional<Warehouse> findWareById(Integer id){return repository.findWareById(id);}

    public Sale add(Sale sale){
        if(sale.getSaleDate() == null){
            throw new IllegalArgumentException("SaleDate can`t be empty");
        }
        if(sale.getAmount() == null){
            throw new IllegalArgumentException("Amount can`t be empty");
        }
        if(sale.getQuantity() == null){
            throw new IllegalArgumentException("Quantity can`t be empty");
        }
        if(sale.getWarehouse() == null){
            throw new IllegalArgumentException("Sale without warehouse");
        }
        return repository.save(sale);
    }

    public List<Sale> findAll(){
        return(List<Sale>) repository.findAll();
    }

    public boolean isExistsId(Integer id){return repository.isExistsId(id);}

    public List<Sale> findByName(String name){
        return repository.findSoldItemsByName(name);
    }

    public List<Sale> findByWarehouseId(Integer id){
        if(!repository.existsByWarehouse_IdEquals(id)){
            throw new SaleNotFoundException("Warehouse with this ID not exists");
        }
        List<Sale> saleList = repository.findSalesByWarehouseId(id);
        if(saleList == null){
            throw new SaleNotFoundException("Sale with this Warehouse not exists");
        }
        return saleList;
    }

    public List<Sale> findByWarehouseName(String name){
        if(!repository.existsByWarehouse_NameEquals(name)){
            throw new SaleNotFoundException("Warehouse with this name not exists");
        }
        List<Sale> saleList = repository.findSalesByWarehouseName(name);
        if(saleList == null){
            throw new SaleNotFoundException("Sale with this Warehouse not exists");
        }
        return saleList;
    }

    public long countInTime(LocalDate saleDateStart, LocalDate saleDateEnd){
        return repository.countSoldItemsInTime(saleDateStart, saleDateEnd);
    }

}
