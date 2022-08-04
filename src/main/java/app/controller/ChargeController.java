package app.controller;


import app.entity.Charge;
import app.exceptions.ChargeNotFoundException;
import app.exceptions.ExpenseItemNotFoundException;
import app.service.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/charge")
public class ChargeController {

    ChargeService chargeService;

    @Autowired
    public ChargeController(ChargeService chargeService) {
        this.chargeService = chargeService;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        try {
            chargeService.deleteById(id);
        }catch (ChargeNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public Charge add(@RequestBody Charge charge) {
        try {
            return chargeService.add(charge);
        } catch (ExpenseItemNotFoundException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/find/id")
    public ResponseEntity<Charge> findById(@RequestParam("id") Integer id){
        try{
            Charge charge = chargeService.findById(id);
            return new ResponseEntity<>(charge, HttpStatus.OK);
        }catch (ChargeNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find/expanse/name")
    public ResponseEntity<Charge> findByExpName(@RequestParam("name") String name){
        try{
            Charge charge = chargeService.findByExpanseName(name);
            return new ResponseEntity<>(charge, HttpStatus.OK);
        }catch (ChargeNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find/expanse/id")
    public ResponseEntity<Charge> findByExpName(@RequestParam("id") Integer id){
        try{
            Charge charge = chargeService.findByExpanseId(id);
            return new ResponseEntity<>(charge, HttpStatus.OK);
        }catch (ChargeNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find/between/amount")
    public ResponseEntity<List<Charge>> findAmountBetween(@RequestParam("amountStart") BigDecimal aStart, @RequestParam("amountEnd") BigDecimal aEnd){
        List<Charge> chargeList = chargeService.findByAmount(aStart,aEnd);
        return new ResponseEntity<>(chargeList, HttpStatus.OK);
    }

    @GetMapping("/find/between/date")
    public ResponseEntity<List<Charge>> findAmountBetween(@RequestParam("chargeDateStart") LocalDate cStart, @RequestParam("chargeDateStart") LocalDate cEnd){
        List<Charge> chargeList = chargeService.findByDate(cStart,cEnd);
        return new ResponseEntity<>(chargeList, HttpStatus.OK);
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Charge>> findAmountBetween(){
        List<Charge> chargeList = chargeService.findAll();
        return new ResponseEntity<>(chargeList, HttpStatus.OK);
    }
}
