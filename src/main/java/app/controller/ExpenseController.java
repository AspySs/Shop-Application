package app.controller;
import app.entity.ExpenseItem;
import app.exceptions.ChargeNotFoundException;
import app.exceptions.ExpenseItemNotFoundException;
import app.service.ExpenseItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/expense")
public class ExpenseController {

    ExpenseItemsService itemsService;

    @Autowired
    public ExpenseController(ExpenseItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") Integer id) {
        try {
            itemsService.deleteById(id);
        }catch (ExpenseItemNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find/id")
    public ResponseEntity<ExpenseItem> findById(@RequestParam("id") Integer id){
        try {
            ExpenseItem item = itemsService.findById(id);
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
        catch (ExpenseItemNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find/name")
    public ResponseEntity<ExpenseItem> findByName(@RequestParam("name") String name){
        try {
            ExpenseItem item = itemsService.findByName(name);
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
        catch (ExpenseItemNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public void add(@RequestBody ExpenseItem expenseItem){
        try{
            itemsService.save(expenseItem);
        }
        catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<ExpenseItem>> findAll(){
        List<ExpenseItem> itemList = itemsService.findAll();
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }


}
