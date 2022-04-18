package app.controller;


import app.entity.Warehouse;
import app.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@Controller
@RequestMapping("/warehouse")
public class WarehouseController {

    WarehouseService service;

    @Autowired
    WarehouseController(WarehouseService service){this.service = service;}

    @GetMapping("/delete/id")
    @ResponseBody
    public String deleteById(@RequestParam("id") Integer id){
        if(service.isExistsId(id)){
            service.deleteById(id);
            return "Warehouse with id = " + id + " has been deleted";
        }
        return "Warehouse with id = " + id + " not found";
    }

    @GetMapping("/delete/name")
    @ResponseBody
    public String deleteByName(@RequestParam("name") String name){
        if(service.isExistsName(name)){
            service.deleteByName(name);
            return "Warehouse with name = " + name + " has been deleted";
        }
        return "Warehouse with name = " + name + " not found";
    }

    @GetMapping("/find/id")
    public String findById(@RequestParam("id") Integer id, Model model){
        model.addAttribute("warehouse", service.findById(id).orElseGet(Warehouse::new));
        return "/Warehouse";
    }

    @GetMapping("/find/name")
    public String findById(@RequestParam("name") String name, Model model){
        model.addAttribute("warehouse", service.findByName(name).orElseGet(Warehouse::new));
        return "/Warehouse";
    }
    @GetMapping("/find/quantity/greater")
    public String findGreaterQ(@RequestParam("quantity") BigDecimal quantity, Model model){
        model.addAttribute("warehouses", service.findByQuantityGreater(quantity));
        return "/Warehouses";
    }

    @GetMapping("/find/quantity/less")
    public String findLessQ(@RequestParam("quantity") BigDecimal quantity, Model model){
        model.addAttribute("warehouses", service.findByQuantityLess(quantity));
        return "/Warehouses";
    }

    @GetMapping("/find/amount/between")
    public String findBetweenA(@RequestParam("amountStart") BigDecimal aStart, @RequestParam("amountEnd") BigDecimal aEnd, Model model){
        model.addAttribute("warehouses", service.findByAmountBetween(aStart,aEnd));
        return "/Warehouses";
    }

    @GetMapping("/add")
    @ResponseBody
    public String add(@RequestParam("id") Integer id, @RequestParam("name") String name, @RequestParam("quantity") BigDecimal quantity, @RequestParam("amount") BigDecimal amount){
        if(!service.isExistsId(id)){
            Warehouse warehouse = new Warehouse();
            warehouse.setId(id);
            warehouse.setName(name);
            warehouse.setQuantity(quantity);
            warehouse.setAmount(amount);
            service.save(warehouse);
            return "Warehouse successful saved";
        }
        return "Warehouse with id = "+id+" is already exist";
    }
}
