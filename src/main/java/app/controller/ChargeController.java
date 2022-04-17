package app.controller;


import app.entity.Charge;
import app.entity.ExpenseItem;
import app.service.ChargeService;
import app.service.ExpenseItemsService;
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
@RequestMapping("/charge")
public class ChargeController {

    ChargeService chargeService;

    @Autowired
    public ChargeController(ChargeService chargeService) {
        this.chargeService = chargeService;
    }

    @GetMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("id") Integer id) {
        if (chargeService.idIsExists(id)) {
            chargeService.deleteById(id);
            return "Charge with id = " + id + " has been deleted";
        }
        return "Charge with id = " + id + " not found";
    }

    @GetMapping("/add")
    @ResponseBody
    public String add(@RequestParam("id") Integer id, @RequestParam("amount") BigDecimal amount, @RequestParam("chargeDate") LocalDate date, @RequestParam("exId") Integer exId) {
        if (chargeService.idIsExists(id)) {
            return "Charge with id = " + id + " already exists";
        }
        Charge charge = new Charge();
        charge.setId(id);
        charge.setAmount(amount);
        charge.setChargeDate(date);
        charge.setExpanseItem(chargeService.findExItemById(exId).orElseGet(ExpenseItem::new));
        chargeService.save(charge);
        return "Charge with id = " + id + " created";
    }

    @GetMapping("/find/id")
    public String findById(@RequestParam("id") Integer id, Model model){
        if(chargeService.idIsExists(id)){
            Charge charge = chargeService.findById(id).orElseGet(Charge::new);
            model.addAttribute("charge", charge);
            return "/Charge";
        }
        return "/ChargeNF";
    }

    @GetMapping("/find/expanse/name")
    public String findByExpName(@RequestParam("name") String name, Model model){
        if(chargeService.isExpItemNameExists(name)){
            Charge charge = chargeService.findByExpanseName(name).orElseGet(Charge::new);
            model.addAttribute("charge", charge);
            return "/Charge";
        }
        return "/ChargeNF";
    }

    @GetMapping("/find/expanse/id")
    public String findByExpName(@RequestParam("id") Integer id, Model model){
        if(chargeService.isExpItemIdExists(id)){
            Charge charge = chargeService.findByExpanseId(id).orElseGet(Charge::new);
            model.addAttribute("charge", charge);
            return "/Charge";
        }
        return "/ChargeNF";
    }

    @GetMapping("/find/between/amount")
    public String findAmountBetween(@RequestParam("amountStart") BigDecimal aStart, @RequestParam("amountEnd") BigDecimal aEnd, Model model){
        List<Charge> chargeList = chargeService.findByAmount(aStart,aEnd);
        model.addAttribute("charges", chargeList);
        return "/Charges";
    }

    @GetMapping("/find/between/date")
    public String findAmountBetween(@RequestParam("chargeDateStart") LocalDate cStart, @RequestParam("chargeDateStart") LocalDate cEnd, Model model){
        List<Charge> chargeList = chargeService.findByDate(cStart,cEnd);
        model.addAttribute("charges", chargeList);
        return "/Charges";
    }

    @GetMapping("/find/all")
    public String findAmountBetween(Model model){
        Iterable<Charge> iterable = chargeService.findAll();
        List<Charge> chargeList = StreamSupport.stream(iterable.spliterator(), false)
                        .collect(Collectors.toList());
        model.addAttribute("charges", chargeList);
        return "/Charges";
    }
}
