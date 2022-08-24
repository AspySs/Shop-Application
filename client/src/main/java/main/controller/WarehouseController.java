package main.controller;


import main.entity.Sale;
import main.entity.User;
import main.entity.Warehouse;
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
public class WarehouseController {
    @Autowired
    private User user;

    @ExceptionHandler({HttpClientErrorException.Unauthorized.class, HttpClientErrorException.Forbidden.class})
    public String handleException(HttpClientErrorException e, Model model) {
        if (HttpStatus.FORBIDDEN.equals(e.getStatusCode())) {
            model.addAttribute("message", "Not enough rights for this (need to be admin)");
        }
        if (HttpStatus.UNAUTHORIZED.equals(e.getStatusCode())) {
            model.addAttribute("message", e.getMessage());
        }
        model.addAttribute("token", user.getToken());
        return "signin/authorizationEx";
    }

    @GetMapping("/warehouses")
    public String getWarehousesPage(Model model) {
        model.addAttribute("title", "Warehouses");

        String url = "http://localhost:8080/warehouse/find/all";
        List<Warehouse> houses = getRequest(url, List.class);
        model.addAttribute("houses", houses);

        model.addAttribute("token", user.getToken());
        return "warehouses/warehouses";
    }

    @GetMapping("/warehouses/find_id/{id}")
    public String getWarehousePage(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("title", "Warehouse №" + id);
        String url = "http://localhost:8080/warehouse/find_id/" + id;
        Warehouse house = getRequest(url, Warehouse.class);
        model.addAttribute("house", house);
        model.addAttribute("token", user.getToken());
        try {
            String url2 = "http://localhost:8080/sale/find/warehouse/id?id=" + id;
            List<Sale> sales = getRequest(url2, List.class);
            model.addAttribute("sales", sales);
        } catch (HttpClientErrorException.NotFound e) {
            return "warehouses/warehouse";
        }

        return "warehouses/warehouse";
    }

    @PostMapping("/warehouses/delete/{id}")
    public String deleteWarehouse(@PathVariable("id") Integer id) {
        String url = "http://localhost:8080/warehouse/delete/" + id;
        postRequest(url, user.getToken(), null, HttpMethod.DELETE, null);
        return "redirect:/warehouses";
    }

    @GetMapping("/warehouses/edit/{id}")
    public String getEditPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("title", "Edit Warehouse");

        String url = "http://localhost:8080/warehouse/find_id/" + id;
        Warehouse house = getRequest(url, Warehouse.class);
        model.addAttribute("house", house);
        model.addAttribute("token", user.getToken());
        return "warehouses/editWarehouse";
    }

    @GetMapping("/warehouses/find/name")
    public String getFindNamePage(Model model) {
        model.addAttribute("title", "Find Warehouse");
        model.addAttribute("token", user.getToken());
        return "warehouses/findName";
    }

    @GetMapping("/warehouses/add")
    public String getAddPage(Model model) {
        model.addAttribute("title", "Add Warehouse");
        model.addAttribute("token", user.getToken());
        return "warehouses/addWarehouse";
    }

    @PostMapping("/warehouses/find/name")
    public String findNameWarehouse(@RequestParam("name") String name, Model model) {
        model.addAttribute("title", "Found Warehouses");
        String url = "http://localhost:8080/warehouse/find/name?name=" + name;
        List<Warehouse> houses = getRequest(url, List.class);
        model.addAttribute("houses", houses);
        model.addAttribute("token", user.getToken());
        return "warehouses/warehouses";
    }

    @PostMapping("/warehouses/edit/{id}")
    public String editWarehouse(@PathVariable("id") long id, @RequestParam("name") String name, @RequestParam("amount") BigDecimal amount, @RequestParam("quantity") BigDecimal quantity) {
        String url = "http://localhost:8080/warehouse/update/" + id;
        String json = "{\n" +
                "  \"id\": " + id + ",\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"quantity\": " + quantity + ",\n" +
                "  \"amount\": " + amount + "\n" +
                "}";
        postRequest(url, user.getToken(), json, HttpMethod.POST, MediaType.APPLICATION_JSON);
        return "redirect:/warehouses/find_id/" + id;
    }

    @PostMapping("/warehouses/add")
    public String addWarehouse(@RequestParam("name") String name, @RequestParam("amount") BigDecimal amount, @RequestParam("quantity") BigDecimal quantity) {
        String url = "http://localhost:8080/warehouse/add";
        String json = "{\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"quantity\": " + quantity + ",\n" +
                "  \"amount\": " + amount + "\n" +
                "}";
        postRequest(url, user.getToken(), json, HttpMethod.POST, MediaType.APPLICATION_JSON);
        return "redirect:/warehouses";
    }

    @GetMapping("/warehouses/find/btw/amount")
    public String findPageAmount(Model model) {
        model.addAttribute("title", "Find by Amount");
        model.addAttribute("token", user.getToken());
        return "warehouses/findAmount";
    }

    @GetMapping("/warehouses/find/less/quantity")
    public String findPageQuantityLess(Model model) {
        model.addAttribute("title", "less");
        model.addAttribute("token", user.getToken());
        return "warehouses/findQuantity";
    }

    @GetMapping("/warehouses/find/greater/quantity")
    public String findPageQuantityGreat(Model model) {
        model.addAttribute("title", "greater");
        model.addAttribute("token", user.getToken());
        return "warehouses/findQuantity";
    }

    @PostMapping("/warehouses/find/quantity")
    public String findWarehouseAmount(@RequestParam("type") String type, @RequestParam("quantity") BigDecimal quantity, Model model) {
        String url = "http://localhost:8080/warehouse/find/quantity/" + type + "?quantity=" + quantity;
        model.addAttribute("title", "Find by Quantity " + type);
        List<Warehouse> houses = getRequest(url, List.class);
        model.addAttribute("houses", houses);
        model.addAttribute("token", user.getToken());
        return "warehouses/found";
    }

    @PostMapping("/warehouses/find/btw/amount")
    public String findWarehouseAmount(@RequestParam("amountStart") BigDecimal amountStart, @RequestParam("amountStop") BigDecimal amountStop, Model model) {
        String url = "http://localhost:8080/warehouse/find/amount/between?amountStart=" + amountStart + "&amountEnd=" + amountStop;
        model.addAttribute("title", "Find by Amount");
        List<Warehouse> houses = getRequest(url, List.class);
        model.addAttribute("houses", houses);
        model.addAttribute("token", user.getToken());
        return "warehouses/found";
    }
}
