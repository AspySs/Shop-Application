package app.service;

import app.entity.Warehouse;
import app.exceptions.ChargeNotFoundException;
import app.exceptions.WarehouseNotFoundException;
import app.repository.SaleRepository;
import app.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    private final WarehouseRepository repository;

    @Autowired
    private final SaleRepository sale_repository;

    @Autowired
    public WarehouseService(WarehouseRepository repository, SaleRepository sale_repository) {
        this.repository = repository;
        this.sale_repository = sale_repository;
    }

    @Transactional
    @Modifying
    public void deleteById(Integer id) {
        if (!repository.isExistsId(id)) {
            throw new WarehouseNotFoundException("Warehouse not found by ID");
        }
        if (sale_repository.existsByWarehouse_IdEquals(id)) {
            throw new IllegalArgumentException("всё ещё есть ссылки в таблице sales");
        }
        repository.deleteById(id);
    }


    public boolean isExistsId(Integer id) {
        return repository.isExistsId(id);
    }

    public boolean isExistsName(String name) {
        return repository.isExistsName(name);
    }

    public Warehouse findById(Integer id) {
        Optional<Warehouse> warehouse = repository.findById(id);
        if (warehouse.isPresent()) {
            return warehouse.get();
        } else {
            throw new WarehouseNotFoundException("Warehouses not found by ID");
        }
    }

    public List<Warehouse> findByQuantityGreater(BigDecimal quantity) {
        List<Warehouse> warehouseList = repository.findByQuantityGreater(quantity);
        if (warehouseList == null) {
            throw new WarehouseNotFoundException("Warehouses not found");
        }
        return warehouseList;
    }

    public List<Warehouse> findByAmountBetween(BigDecimal amountStart, BigDecimal amountEnd) {
        List<Warehouse> warehouseList = repository.findByAmountBetween(amountStart, amountEnd);
        if (warehouseList == null) {
            throw new WarehouseNotFoundException("Warehouses not found");
        }
        return warehouseList;
    }

    public List<Warehouse> findByQuantityLess(BigDecimal quantity) {
        List<Warehouse> warehouseList = repository.findByQuantityLess(quantity);
        if (warehouseList == null) {
            throw new WarehouseNotFoundException("Warehouses not found");
        }
        return warehouseList;
    }

    public List<Warehouse> findByName(String name) {
        List<Warehouse> warehouseList = repository.findByNameEquals(name);
        if (warehouseList == null) {
            throw new WarehouseNotFoundException("Warehouses not found");
        }
        return warehouseList;
    }

    public Warehouse add(Warehouse warehouse) {
        if (warehouse.getAmount() == null) {
            throw new IllegalArgumentException("Amount can`t be empty");
        }
        if (warehouse.getName() == null) {
            throw new IllegalArgumentException("Name can`t be empty");
        }
        if (warehouse.getQuantity() == null) {
            throw new IllegalArgumentException("Quantity can`t be empty");
        }
        return repository.save(warehouse);
    }

    public List<Warehouse> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Modifying
    public void update(String name, BigDecimal quantity, BigDecimal amount, Integer id) {
        if (!repository.isExistsId(id)) {
            throw new ChargeNotFoundException("Warehouse not found by ID");
        }
        if (name.isEmpty() || quantity == null || amount == null) {
            throw new IllegalArgumentException("Parameters can`t be empty!");
        }
        repository.warehouseUpdate(name, quantity, amount, id);
    }
}
