package service;

import entity.ExpenseItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ExpenseItemRepository;

import java.util.Optional;

@Service
public class ExpenseItemsService {
    @Autowired
    private final ExpenseItemRepository repository;

    @Autowired
    public ExpenseItemsService(ExpenseItemRepository repository){this.repository = repository;}

    public void deleteById(Integer id){
        repository.deleteById(id);
    }

    public ExpenseItem save(ExpenseItem expenseItem){
        return repository.save(expenseItem);
    }

    public Optional<ExpenseItem> findById(Integer id){
        return repository.findById(id);
    }

    public boolean isExistsName(String name){
        return repository.isExistsByName(name);
    }

    public boolean isExistsId(Integer id){
        return repository.isExistsById(id);
    }

    public void deleteByName(String name){
        repository.deleteByName(name);
    }

    public Optional<ExpenseItem> findByName(String name){
        return repository.findByName(name);
    }



}
