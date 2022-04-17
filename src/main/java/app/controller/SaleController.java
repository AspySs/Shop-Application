package app.controller;


import app.entity.Sale;
import app.entity.Warehouse;
import app.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @GetMapping("/delete/id")
    @ResponseBody
    public String delete(@RequestParam("id") Integer id) {
        if (service.isExistsId(id)) {
            service.deleteById(id);
            return "Sale with id = " + id + " has been deleted";
        }
        return "Sale with id = " + id + " not found";
    }

    @GetMapping("/find/id")
    public String findById(@RequestParam("id") Integer id, Model model){
        model.addAttribute("sale", service.findById(id).orElseGet(Sale::new));
        return "/Sale";
    }

    @GetMapping("/find/warehouse/id")
    public String findByWareId(@RequestParam("id") Integer id, Model model){
        model.addAttribute("sale", service.findByWarehouseId(id));
        return "/Sales";
    }

    @GetMapping("/find/name")
    public String findByName(@RequestParam("name") String name, Model model){
        model.addAttribute("sales", service.findByName(name));
        return "/Sales";
    }

    @GetMapping("/find/warehouse/name")
    public String findByWareName(@RequestParam("name") String name, Model model){
        model.addAttribute("sales", service.findByWarehouseName(name));
        return "/Sales";
    }

    @GetMapping("/find/all")
    public String findByName( Model model){
        Iterable<Sale> iterable = service.findAll();
        List<Sale> list = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
        model.addAttribute("sales", list);
        return "/Sales";
    }

    @GetMapping("/count/time")
    @ResponseBody
    public String countByTime(@RequestParam("saleDateStart") LocalDate saleDateStart, @RequestParam("saleDateEnd") LocalDate saleDateEnd, Model model){
        return "number of Sales in time: "+saleDateStart+" - "+saleDateEnd+" = "+service.countInTime(saleDateStart, saleDateEnd);
    }

    @GetMapping("/add")
    @ResponseBody
    public String add(@RequestParam("id") Integer id, @RequestParam("quantity") BigDecimal quantity, @RequestParam("amount") BigDecimal amount, @RequestParam("sale_date") LocalDate date, @RequestParam("warehouse_id") Integer wid){
        if(!service.isExistsId(id)){
            Sale sale = new Sale();
            sale.setId(id);
            sale.setQuantity(quantity);
            sale.setAmount(amount);
            sale.setSaleDate(date);
            Warehouse warehouse = service.findWareById(wid).orElseGet(Warehouse::new);
            sale.setWarehouse(warehouse);
            service.save(sale);
            return "Sale successful added !";
        }
        return "Sale with id="+id+" already exists";
    }


}
