package app.controller;

import app.entity.ExpenseItem;
import app.exceptions.ExpenseItemNotFoundException;
import app.exceptions.ItemIsBusyException;
import app.service.ExpenseItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    public ResponseEntity<ExpenseItem> deleteById(@PathVariable("id") Integer id) {
        try {
            itemsService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ExpenseItemNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (ItemIsBusyException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/find_id/{id}")
    public ResponseEntity<ExpenseItem> findById(@PathVariable("id") Integer id) {
        try {
            ExpenseItem item = itemsService.findById(id);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (ExpenseItemNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find/name")
    public ResponseEntity<List<ExpenseItem>> findByName(@RequestParam("name") String name) {
        try {
            List<ExpenseItem> item = itemsService.findByName(name);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (ExpenseItemNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ExpenseItem> add(@RequestBody ExpenseItem expenseItem) {
        try {
            itemsService.save(expenseItem);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<ExpenseItem>> findAll() {
        List<ExpenseItem> itemList = itemsService.findAll();
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ExpenseItem> updateItem(@RequestBody ExpenseItem item) {
        try {
            itemsService.update(item.getName(), item.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ExpenseItemNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
