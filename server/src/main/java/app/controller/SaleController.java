package app.controller;


import app.entity.Charge;
import app.entity.Sale;
import app.entity.Warehouse;
import app.exceptions.ChargeNotFoundException;
import app.exceptions.ExpenseItemNotFoundException;
import app.exceptions.SaleNotFoundException;
import app.exceptions.WarehouseNotFoundException;
import app.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/sale")
public class SaleController {

    SaleService service;

    @Autowired
    SaleController(SaleService service){this.service = service;}


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Sale> delete(@PathVariable("id") Integer id) {
        try {
            service.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (SaleNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find_id/{id}")
    public ResponseEntity<Sale> findById(@PathVariable("id") Integer id){
        try {
            Sale sale = service.findById(id);
            return new ResponseEntity<>(sale, HttpStatus.OK);
        }
        catch (SaleNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find/warehouse/id")
    public ResponseEntity<List<Sale>> findByWareId(@RequestParam("id") Integer id){
        try {
            List<Sale> saleList = service.findByWarehouseId(id);
            return new ResponseEntity<>(saleList, HttpStatus.OK);
        }
         catch (SaleNotFoundException e){
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
         }
    }

    @PostMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Sale> update(@PathVariable("id") Integer id, @RequestBody Sale sale){
        try {
            service.update(sale.getQuantity(), sale.getAmount(), sale.getSaleDate(), sale.getWarehouse().getId(), id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (WarehouseNotFoundException | SaleNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    @GetMapping("/find/warehouse/name")
    public ResponseEntity<List<Sale>> findByWareName(@RequestParam("name") String name){
        try{
           List<Sale> saleList = service.findByWarehouseName(name);
           return new ResponseEntity<>(saleList, HttpStatus.OK);
        }
        catch (SaleNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find/date")
    public ResponseEntity<List<Sale>> findByDate(@RequestParam("start") String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dat = LocalDate.parse(date, formatter);
        try {
            List<Sale> saleList = service.findBySaleDate(dat);
            return new ResponseEntity<>(saleList, HttpStatus.OK);
        }
        catch (SaleNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @GetMapping("/find/amount")
    public ResponseEntity<List<Sale>> findAmountBetween(@RequestParam("aStart") BigDecimal aStart, @RequestParam("aStop") BigDecimal aStop){
        try {
            List<Sale> saleList = service.findByAmountBetween(aStart,aStop);
            return new ResponseEntity<>(saleList, HttpStatus.OK);
        }
        catch (SaleNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find/quantity")
    public ResponseEntity<List<Sale>> findQuantityBetween(@RequestParam("qStart") BigDecimal qStart, @RequestParam("qStop") BigDecimal qStop){
        try {
            List<Sale> saleList = service.findByQuantityBetween(qStart,qStop);
            return new ResponseEntity<>(saleList, HttpStatus.OK);
        }
        catch (SaleNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Sale>> findAll(){
        List<Sale> saleList = service.findAll();
        return new ResponseEntity<>(saleList, HttpStatus.OK);
    }

    @GetMapping("/count/time")
    @ResponseBody
    public ResponseEntity<Long> countByTime(@RequestParam("saleDateStart") LocalDate saleDateStart, @RequestParam("saleDateEnd") LocalDate saleDateEnd, Model model){
        return new ResponseEntity<>(service.countInTime(saleDateStart, saleDateEnd), HttpStatus.OK);
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Sale> add(@RequestBody Sale sale){
        try {
            service.add(sale);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        catch (WarehouseNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


}
