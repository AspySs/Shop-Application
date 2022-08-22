package main.controller;


import main.entity.Charge;
import main.entity.Sale;
import main.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

import static main.utils.Utils.getRequest;
import static main.utils.Utils.postRequest;

@Controller
public class SaleController {
    @Autowired
    private User user;

    @GetMapping("/sales")
    public String getSalesPage(Model model) {
        model.addAttribute("title", "Sales");

        String url = "http://localhost:8080/sale/find/all";
        List<Sale> sales = getRequest(url, List.class);
        model.addAttribute("sales", sales);

        return "sales/sales";
    }

    @GetMapping("/sales/find_id/{id}")
    public String getSalePage(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("title", "Sale №" + id);

        String url = "http://localhost:8080/sale/find_id/" + id;

        Sale sale = getRequest(url, Sale.class);
        model.addAttribute("sale", sale);

        return "sales/sale";
    }

    @PostMapping("/sales/delete/{id}")
    public String deleteSale(@PathVariable("id") Integer id) {
        String url = "http://localhost:8080/sale/delete/" + id;
        postRequest(url, user.getToken(), null, HttpMethod.DELETE, null);
        return "redirect:/sales";
    }

    @GetMapping("/sales/add")
    public String getAddPage(Model model) {
        model.addAttribute("title", "Add Sale");
        return "sales/addSale";
    }

    @GetMapping("/sales/edit/{id}")
    public String getEditPage(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("title", "Edit Sale");
        String url = "http://localhost:8080/sale/find_id/" + id;

        Sale sale = getRequest(url, Sale.class);
        model.addAttribute("sale", sale);
        return "sales/editSale";
    }

    @PostMapping("/sales/edit/{id}")
    public String getEditPage(Model model, @RequestParam("date") String date, @RequestParam("amount") BigDecimal amount, @RequestParam("quantity") BigDecimal quantity, @RequestParam("wId") Integer wId, @PathVariable("id") Integer id) {
        model.addAttribute("title", "Edit Sale");
        String url = "http://localhost:8080/sale/update/" + id;
        String json = "{\n" +
                "  \"quantity\": "+quantity+",\n" +
                "  \"amount\": "+amount+",\n" +
                "  \"saleDate\": \""+date+"\",\n" +
                "  \"warehouse\": {\n" +
                "    \"id\": "+wId+"\n" +
                "  }\n" +
                "}";
        postRequest(url, user.getToken(), json, HttpMethod.POST, MediaType.APPLICATION_JSON);
        return "redirect:/sales/find_id/"+id;
    }

    @PostMapping("/sales/add")
    public String addSale(@RequestParam("date") String date, @RequestParam("amount") BigDecimal amount, @RequestParam("quantity") BigDecimal quantity, @RequestParam("wId") Integer wId) {
        String url = "http://localhost:8080/sale/add";
        String json = "{\n" +
                "  \"quantity\": "+quantity+",\n" +
                "  \"amount\": "+amount+",\n" +
                "  \"saleDate\": \""+date+"\",\n" +
                "  \"warehouse\": {\n" +
                "    \"id\": "+wId+"\n" +
                "  }\n" +
                "}";
        postRequest(url, user.getToken(), json, HttpMethod.POST, MediaType.APPLICATION_JSON);
        return "redirect:/sales";
    }
}
