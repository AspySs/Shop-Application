package app.service;

import app.entity.ExpenseItem;
import app.exceptions.ExpenseItemNotFoundException;
import app.exceptions.ItemIsBusyException;
import app.repository.ChargeRepository;
import app.repository.ExpenseItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseItemsService {
    @Autowired
    private final ExpenseItemRepository repository;

    @Autowired
    private final ChargeRepository chargeRepository;

    @Autowired
    public ExpenseItemsService(ExpenseItemRepository repository, ChargeRepository chargeRepository) {
        this.repository = repository;
        this.chargeRepository = chargeRepository;
    }

    @Transactional
    @Modifying
    public void deleteById(Integer id) {
        if (!repository.isExistsById(id)) {
            throw new ExpenseItemNotFoundException("Expense item not found by ID");
        }
        if (chargeRepository.isExpItemIdExists(id)) {
            throw new ItemIsBusyException("всё ещё есть ссылки в таблице \"charges\"");
        }
        repository.deleteById(id);
    }

    public ExpenseItem save(ExpenseItem expenseItem) {
        if (expenseItem.getName().isEmpty()) {
            throw new IllegalArgumentException("Name can`t be empty!");
        }
        return repository.save(expenseItem);
    }

    public ExpenseItem findById(Integer id) {
        Optional<ExpenseItem> item = repository.findById(id);
        if (item.isPresent()) {
            return item.get();
        } else {
            throw new ExpenseItemNotFoundException("Expense item not found by ID");
        }
    }

    public boolean isExistsName(String name) {
        return repository.isExistsByName(name);
    }

    public boolean isExistsId(Integer id) {
        return repository.isExistsById(id);
    }


    public List<ExpenseItem> findByName(String name) {
        List<ExpenseItem> item = repository.findByName(name);
        if (!item.isEmpty()) {
            return item;
        } else {
            throw new ExpenseItemNotFoundException("Expense item not found by Name");
        }
    }

    public List<ExpenseItem> findAll() {
        return repository.findAll();
    }

    public void update(String name, Integer id) {
        if (!repository.isExistsById(id)) {
            throw new ExpenseItemNotFoundException("Expense item not found by ID");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Expense item name can`t be empty");
        }
        repository.updateNameByIdEquals(name, id);
    }


}
