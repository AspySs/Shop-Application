package app.controller;

import app.entity.Warehouse;
import app.exceptions.ChargeNotFoundException;
import app.exceptions.WarehouseNotFoundException;
import app.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/warehouse")
public class WarehouseController {

    WarehouseService service;

    @Autowired
    WarehouseController(WarehouseService service) {
        this.service = service;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Warehouse> delete(@PathVariable("id") Integer id) {
        try {
            service.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (WarehouseNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/find_id/{id}")
    public ResponseEntity<Warehouse> findById(@PathVariable("id") Integer id) {
        try {
            Warehouse warehouse = service.findById(id);
            return new ResponseEntity<>(warehouse, HttpStatus.OK);
        } catch (WarehouseNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find/name")
    public ResponseEntity<List<Warehouse>> findByName(@RequestParam("name") String name) {
        try {
            List<Warehouse> warehouseList = service.findByName(name);
            return new ResponseEntity<>(warehouseList, HttpStatus.OK);
        } catch (WarehouseNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find/quantity/greater")
    public ResponseEntity<List<Warehouse>> findGreaterQ(@RequestParam("quantity") BigDecimal quantity) {
        try {
            List<Warehouse> warehouseList = service.findByQuantityGreater(quantity);
            return new ResponseEntity<>(warehouseList, HttpStatus.OK);
        } catch (WarehouseNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find/quantity/less")
    public ResponseEntity<List<Warehouse>> findLessQ(@RequestParam("quantity") BigDecimal quantity) {
        try {
            List<Warehouse> warehouseList = service.findByQuantityLess(quantity);
            return new ResponseEntity<>(warehouseList, HttpStatus.OK);
        } catch (WarehouseNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find/amount/between")
    public ResponseEntity<List<Warehouse>> findBetweenA(@RequestParam("amountStart") BigDecimal aStart, @RequestParam("amountEnd") BigDecimal aEnd) {
        try {
            List<Warehouse> warehouseList = service.findByAmountBetween(aStart, aEnd);
            return new ResponseEntity<>(warehouseList, HttpStatus.OK);
        } catch (WarehouseNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Warehouse> add(@RequestBody Warehouse warehouse) {
        try {
            service.add(warehouse);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Warehouse>> findAll() {
        List<Warehouse> warehouseList = service.findAll();
        return new ResponseEntity<>(warehouseList, HttpStatus.OK);
    }

    @PostMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Warehouse> update(@PathVariable("id") Integer id, @RequestBody Warehouse warehouse) {
        try {
            service.update(warehouse.getName(), warehouse.getQuantity(), warehouse.getAmount(), id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ChargeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
