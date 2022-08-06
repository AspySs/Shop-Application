package app.controller;


import app.entity.Charge;
import app.entity.Sale;
import app.entity.Warehouse;
import app.exceptions.ChargeNotFoundException;
import app.exceptions.ExpenseItemNotFoundException;
import app.exceptions.SaleNotFoundException;
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
    public void delete(@PathVariable("id") Integer id) {
        try {
            service.deleteById(id);
        }catch (SaleNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find/id")
    public ResponseEntity<Sale> findById(@RequestParam("id") Integer id){
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

    @GetMapping("/find/name")
    public ResponseEntity<List<Sale>> findByName(@RequestParam("name") String name){
        try{
            List<Sale> saleList = service.findByName(name);
            return new ResponseEntity<>(saleList, HttpStatus.OK);
        }
        catch (SaleNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
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

    @GetMapping("/find/all")
    public ResponseEntity<List<Sale>> findAll(){
        List<Sale> saleList = service.findAll();
        return new ResponseEntity<>(saleList, HttpStatus.OK);
    }

    @GetMapping("/count/time")
    @ResponseBody
    public Long countByTime(@RequestParam("saleDateStart") LocalDate saleDateStart, @RequestParam("saleDateEnd") LocalDate saleDateEnd, Model model){
        return service.countInTime(saleDateStart, saleDateEnd);
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public Sale add(@RequestBody Sale sale){
        try {
            return service.add(sale);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


}
