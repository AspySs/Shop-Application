package app.service;

import app.entity.Charge;
import app.entity.ExpenseItem;
import app.exceptions.ExpenseItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import app.repository.ExpenseItemRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseItemsService {
    @Autowired
    private final ExpenseItemRepository repository;

    @Autowired
    public ExpenseItemsService(ExpenseItemRepository repository){this.repository = repository;}

    @Transactional
    @Modifying
    public void deleteById(Integer id){
        if(!repository.isExistsById(id)){
            throw new ExpenseItemNotFoundException("Expense item not found by ID");
        }
        repository.deleteById(id);
    }

    public ExpenseItem save(ExpenseItem expenseItem){
        if(expenseItem.getName() == null){
            throw new IllegalArgumentException("Name can`t be empty!");
        }
        return repository.save(expenseItem);
    }

    public ExpenseItem findById(Integer id){
        Optional<ExpenseItem> item =  repository.findById(id);
        if(item.isPresent()){
            return item.get();
        }else{
            throw new ExpenseItemNotFoundException("Expense item not found by ID");
        }
    }

    public boolean isExistsName(String name){
        return repository.isExistsByName(name);
    }

    public boolean isExistsId(Integer id){
        return repository.isExistsById(id);
    }


    public ExpenseItem findByName(String name){
        Optional<ExpenseItem> item =  repository.findByName(name);
        if(item.isPresent()){
            return item.get();
        }else{
            throw new ExpenseItemNotFoundException("Expense item not found by Name");
        }
    }
    public List<ExpenseItem> findAll(){return(List<ExpenseItem>) repository.findAll();}


}
