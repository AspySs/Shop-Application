package app.service;

import app.entity.ExpenseItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import app.repository.ExpenseItemRepository;

import javax.transaction.Transactional;
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

    @Transactional
    @Modifying
    public void deleteByName(String name){
        repository.deleteByName(name);
    }

    public Optional<ExpenseItem> findByName(String name){
        return repository.findByName(name);
    }



}
