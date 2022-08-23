package main.controller;

import main.entity.Charge;
import main.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.List;

import static main.utils.Utils.getRequest;
import static main.utils.Utils.postRequest;

@Controller
public class ChargeController {
    @Autowired
    private User user;

    @ExceptionHandler(HttpClientErrorException.class)
    public String handleException(HttpClientErrorException e, Model model) {
        if (HttpStatus.FORBIDDEN.equals(e.getStatusCode())) {
            model.addAttribute("message", "Not enough rights for this (need to be admin)");
        }
        if (HttpStatus.UNAUTHORIZED.equals(e.getStatusCode())) {
            model.addAttribute("message", e.getMessage());
        }
        return "signin/authorizationEx";
    }

    @GetMapping("/charges")
    public String getChargesPage(Model model) {
        model.addAttribute("title", "Charges");

        String url = "http://localhost:8080/charge/find/all";
        List<Charge> charges = getRequest(url, List.class);
        model.addAttribute("charges", charges);
        return "charges/charges";
    }

    @GetMapping("/charges/find_id/{id}")
    public String getChargePage(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("title", "Charge №" + id);

        String url = "http://localhost:8080/charge/find_id/" + id;

        Charge charge = getRequest(url, Charge.class);
        model.addAttribute("charge", charge);

        return "charges/charge";
    }

    @PostMapping("/charges/delete/{id}")
    public String deleteCharge(@PathVariable("id") Integer id, Model model) {
        String url = "http://localhost:8080/charge/delete/" + id;
        postRequest(url, user.getToken(), null, HttpMethod.DELETE, null);
        return "redirect:/charges";
    }

    @GetMapping("/charges/edit/{id}")
    public String getEditPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("title", "Edit Charge");

        String url = "http://localhost:8080/charge/find_id/" + id;
        Charge charge = getRequest(url, Charge.class);
        model.addAttribute("charge", charge);
        return "charges/editCharge";
    }

    @PostMapping("/charges/edit/{id}")
    public String editCharge(@PathVariable("id") long id, @RequestParam("newAmount") BigDecimal newAmount,
                             @RequestParam("newChargeDate") String newChargeDate, @RequestParam("newExId") Integer newExId) {
        String url = "http://localhost:8080/charge/update/" + id;
        String json = "{\n" +
                "  \"amount\":" + newAmount + ",\n" +
                "  \"chargeDate\":\"" + newChargeDate + "\",\n" +
                "  \"expanseItem\":{\"id\":" + newExId + "}\n" +
                "}";
        postRequest(url, user.getToken(), json, HttpMethod.POST, MediaType.APPLICATION_JSON);
        return "redirect:/charges/find_id/" + id;
    }

    @PostMapping("/charges/add")
    public String addCharge(@RequestParam("Amount") BigDecimal newAmount,
                            @RequestParam("ChargeDate") String newChargeDate, @RequestParam("ExId") Integer newExId) {
        String url = "http://localhost:8080/charge/add";
        String json = "{\n" +
                "  \"amount\":" + newAmount + ",\n" +
                "  \"chargeDate\":\"" + newChargeDate + "\",\n" +
                "  \"expanseItem\":{\"id\":" + newExId + "}\n" +
                "}";


        postRequest(url, user.getToken(), json, HttpMethod.POST, MediaType.APPLICATION_JSON);
        return "redirect:/charges";
    }

    @GetMapping("/charges/add")
    public String addChargePage(Model model) {
        model.addAttribute("title", "Add charge");
        return "charges/addCharge";
    }

    @GetMapping("/charges/find/btw/amount")
    public String findChargePageAmount(Model model) {
        model.addAttribute("title", "Find by Amount");
        return "charges/findAmount";
    }

    @GetMapping("/charges/find/btw/date")
    public String findChargePageDate(Model model) {
        model.addAttribute("title", "Find by Date");
        return "charges/findDate";
    }

    @GetMapping("/charges/find/ex/name")
    public String findChargePageName(Model model) {
        model.addAttribute("title", "Find by Expense name");
        return "charges/findName";
    }

    @GetMapping("/charges/find/ex/id")
    public String findChargePageID(Model model) {
        model.addAttribute("title", "Find by Expense ID");
        return "charges/findID";
    }

    @PostMapping("/charges/find/ex/id")
    public String findChargeExId(@RequestParam("exId") Integer id, Model model) {
        String url = "http://localhost:8080/charge/find/expanse/id?id=" + id;
        model.addAttribute("title", "Find by ExID");
        List<Charge> charges = getRequest(url, List.class);
        model.addAttribute("charges", charges);
        return "charges/foundEx";
    }

    @PostMapping("/charges/find/ex/name")
    public String findChargeExName(@RequestParam("exName") String name, Model model) {
        String url = "http://localhost:8080/charge/find/expanse/name?name=" + name;
        model.addAttribute("title", "Find by ExName");
        List<Charge> charges = getRequest(url, List.class);
        model.addAttribute("charges", charges);
        return "charges/foundEx";
    }

    @PostMapping("/charges/find/btw/date")
    public String findChargeDate(@RequestParam("chargeDateStart") String chargeDateStart, @RequestParam("chargeDateStop") String chargeDateStop, Model model) {
        String url = "http://localhost:8080/charge/find/between/date?chargeDateStart=" + chargeDateStart + "&chargeDateStop=" + chargeDateStop;
        model.addAttribute("title", "Find by Date");
        List<Charge> charges = getRequest(url, List.class);
        model.addAttribute("charges", charges);
        return "charges/found";
    }

    @PostMapping("/charges/find/btw/amount")
    public String findChargeAmount(@RequestParam("amountStart") BigDecimal amountStart, @RequestParam("amountStop") BigDecimal amountStop, Model model) {
        String url = "http://localhost:8080/charge/find/between/amount?amountStart=" + amountStart + "&amountEnd=" + amountStop;
        model.addAttribute("title", "Find by Amount");
        List<Charge> charges = getRequest(url, List.class);
        model.addAttribute("charges", charges);
        return "charges/found";
    }
}
