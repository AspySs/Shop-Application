package main.controller;

import main.entity.Charge;
import main.entity.ExpenseItem;
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

import java.util.List;

import static main.utils.Utils.getRequest;
import static main.utils.Utils.postRequest;

@Controller
public class ExpenseItemController {
    @Autowired
    private User user;

    @GetMapping("/expenseitems")
    public String getChargesPage(Model model) {
        model.addAttribute("title", "ExpenseItems");

        String url = "http://localhost:8080/expense/find/all";
        List<ExpenseItem> items = getRequest(url, List.class);
        model.addAttribute("items", items);

        return "items/items";
    }

    @PostMapping("/expenseitems/delete/{id}")
    public String deleteItem(@PathVariable("id") Integer id) {
        String url = "http://localhost:8080/expense/delete/" + id;
        postRequest(url, user.getToken(), null, HttpMethod.DELETE, null);
        return "redirect:/expenseitems";
    }

    @GetMapping("/expenseitems/add")
    public String addItemPage(Model model) {
        model.addAttribute("title", "Add ExpenseItem");
        return "items/addItem";
    }

    @PostMapping("/expenseitems/add")
    public String addItem(@RequestParam("name") String name) {
        String url = "http://localhost:8080/expense/add";
        String json = "{\n" +
                "  \"name\":\""+name+"\"\n" +
                "}";

        postRequest(url, user.getToken(), json, HttpMethod.POST, MediaType.APPLICATION_JSON);
        return "redirect:/expenseitems";
    }

    @GetMapping("/expenseitems/find/id")
    public String findItemPageID(Model model) {
        model.addAttribute("title", "Find by ID");
        return "items/findID";
    }

    @GetMapping("/expenseitems/find/name")
    public String findItemPageName(Model model) {
        model.addAttribute("title", "Find by Name");
        return "items/findName";
    }

    @GetMapping("/expenseitems/edit/{id}")
    public String editItemPageName(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("title", "Edit Item");

        String url = "http://localhost:8080/expense/find_id/" + id;
        ExpenseItem item = getRequest(url, ExpenseItem.class);
        model.addAttribute("item", item);

        return "items/editItem";
    }

    @PostMapping("/expenseitems/edit/{id}")
    public String editItem(@PathVariable("id") Integer id, @RequestParam String name) {
        String url = "http://localhost:8080/expense/update";
        String json = "{\n" +
                "  \"id\":"+id+",\n" +
                "  \"name\":\""+name+"\"\n" +
                "}";
        postRequest(url, user.getToken(), json, HttpMethod.POST, MediaType.APPLICATION_JSON);

        return "redirect:/expenseitems";
    }


    @PostMapping("/expenseitems/find/id")
    public String findItemID(@RequestParam("id") Integer id, Model model) {
        String url = "http://localhost:8080/expense/find_id/" + id;
        model.addAttribute("title", "Find by ID");
        ExpenseItem item = getRequest(url, ExpenseItem.class);
        model.addAttribute("item", item);
        String url2 = "http://localhost:8080/charge/find/expanse/id?id=" + id;
        List<Charge> charges = getRequest(url2, List.class);
        model.addAttribute("charges", charges);
        return "items/item";
    }

    @PostMapping("/expenseitems/find/name")
    public String findItemName(@RequestParam("name") String name, Model model) {
        String url = "http://localhost:8080/expense/find/name?name=" + name;
        model.addAttribute("title", "Find by Name");
        List<ExpenseItem> items = getRequest(url, List.class);
        model.addAttribute("items", items);
        return "items/items";
    }
}
