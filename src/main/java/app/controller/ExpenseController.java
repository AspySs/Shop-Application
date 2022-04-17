package app.controller;
import app.entity.ExpenseItem;
import app.service.ExpenseItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/expense")
public class ExpenseController {

    ExpenseItemsService itemsService;

    @Autowired
    public ExpenseController(ExpenseItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @GetMapping("/delete/id")
    @ResponseBody
    public String deleteById(@RequestParam("id") Integer id) {
        if (itemsService.isExistsId(id)) {
            itemsService.deleteById(id);
            return "ExpenseItem with id = " + id + " has been deleted";
        }
        return "ExpenseItem with id = " + id + " not found";
    }

    @GetMapping("/delete/name")
    @ResponseBody
    public String deleteByName(@RequestParam("name") String name) {
        if (itemsService.isExistsName(name)) {
            itemsService.deleteByName(name);
            return "ExpenseItem with name = " + name + " has been deleted";
        }
        return "ExpenseItem with name = " + name + " not found";
    }

    @GetMapping("/find/id")
    public String findById(@RequestParam("id") Integer id, Model model){
        model.addAttribute("exItem", itemsService.findById(id).orElseGet(ExpenseItem::new));
        return "/Expense";
    }

    @GetMapping("/find/name")
    public String findById(@RequestParam("name") String name, Model model){
        model.addAttribute("exItem", itemsService.findByName(name).orElseGet(ExpenseItem::new));
        return "/Expense";
    }

    @GetMapping("/add")
    @ResponseBody
    public String add(@RequestParam("id") Integer id, @RequestParam("name") String name, Model model){
        if(!itemsService.isExistsId(id)) {
            ExpenseItem expenseItem = new ExpenseItem();
            expenseItem.setId(id);
            expenseItem.setName(name);
            itemsService.save(expenseItem);
            return "expenseItem added succesful!";
        }
        return "expenseItem already exist!";
    }


}
