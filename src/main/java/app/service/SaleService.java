package app.service;

import app.entity.Sale;
import app.entity.Warehouse;
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
        repository.deleteById(id);
    }

    public Optional<Sale> findById(Integer id){
        return repository.findSaleById(id);
    }

    public Optional<Warehouse> findWareById(Integer id){return repository.findWareById(id);}

    public Sale save(Sale sale){
        return repository.save(sale);
    }

    public Iterable<Sale> findAll(){
        return repository.findAll();
    }

    public boolean isExistsId(Integer id){return repository.isExistsId(id);}

    public List<Sale> findByName(String name){
        return repository.findSoldItemsByName(name);
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

}
