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
import java.time.format.DateTimeFormatter;
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
    public ResponseEntity<Charge> delete(@PathVariable("id") Integer id) {
        try {
            chargeService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ChargeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Charge> add(@RequestBody Charge charge) {
        try {
            chargeService.add(charge);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ExpenseItemNotFoundException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Charge> update(@PathVariable("id") Integer id, @RequestBody Charge charge) {
        try {
            chargeService.update(charge.getAmount(), charge.getChargeDate(), charge.getExpanseItem().getId(), id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ChargeNotFoundException | ExpenseItemNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find_id/{id}")
    public ResponseEntity<Charge> findById(@PathVariable("id") Integer id) {
        try {
            Charge charge = chargeService.findById(id);
            return new ResponseEntity<>(charge, HttpStatus.OK);
        } catch (ChargeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find/expanse/name")
    public ResponseEntity<List<Charge>> findByExpName(@RequestParam("name") String name) {
        try {
            List<Charge> charges = chargeService.findByExpanseName(name);
            return new ResponseEntity<>(charges, HttpStatus.OK);
        } catch (ChargeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find/expanse/id")
    public ResponseEntity<List<Charge>> findByExpName(@RequestParam("id") Integer id) {
        try {
            List<Charge> charges = chargeService.findByExpanseId(id);
            return new ResponseEntity<>(charges, HttpStatus.OK);
        } catch (ChargeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find/between/amount")
    public ResponseEntity<List<Charge>> findAmountBetween(@RequestParam("amountStart") BigDecimal aStart, @RequestParam("amountEnd") BigDecimal aEnd) {
        List<Charge> chargeList = chargeService.findByAmount(aStart, aEnd);
        return new ResponseEntity<>(chargeList, HttpStatus.OK);
    }

    @GetMapping("/find/between/date")
    public ResponseEntity<List<Charge>> findDateBetween(@RequestParam("chargeDateStart") String cStart, @RequestParam("chargeDateStop") String cEnd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(cStart, formatter);
        LocalDate stop = LocalDate.parse(cEnd, formatter);
        List<Charge> chargeList = chargeService.findByDate(start, stop);
        return new ResponseEntity<>(chargeList, HttpStatus.OK);
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Charge>> findAll() {
        List<Charge> chargeList = chargeService.findAll();
        return new ResponseEntity<>(chargeList, HttpStatus.OK);
    }
}
