package service;

import entity.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.WarehouseRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    private final WarehouseRepository repository;

    @Autowired
    public WarehouseService(WarehouseRepository repository){this.repository = repository;}

    public void deleteById(Integer id){
        repository.deleteById(id);
    }

    public void deleteByName(String name){
        repository.deleteByName(name);
    }

    public Optional<Warehouse> findById(Integer id){
        return repository.findWarehouseById(id);
    }

    public List<Warehouse> findByQuantityGreater(BigDecimal quantity){
        return repository.findByQuantityGreater(quantity);
    }

    public List<Warehouse> findByAmountBetween(BigDecimal amountStart, BigDecimal amountEnd){
        return repository.findByAmountBetween(amountStart, amountEnd);
    }

    public List<Warehouse> findByQuantityLess(BigDecimal quantity){
        return repository.findByQuantityLess(quantity);
    }

    public Optional<Warehouse> findByName(String name){
        return repository.findByName(name);
    }

    public Warehouse save(Warehouse warehouse){
        return repository.save(warehouse);
    }
}
