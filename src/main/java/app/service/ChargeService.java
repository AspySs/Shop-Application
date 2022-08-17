package app.service;

import app.entity.Charge;
import app.entity.ExpenseItem;
import app.exceptions.ChargeNotFoundException;
import app.exceptions.ExpenseItemNotFoundException;
import app.repository.ExpenseItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import app.repository.ChargeRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ChargeService {
    @Autowired
    private final ChargeRepository repository;

    @Autowired
    private ExpenseItemRepository ex_repos;

    @Autowired
    public ChargeService(ChargeRepository repository){this.repository = repository;}

    @Transactional
    @Modifying
    public void deleteById(Integer id){
        if(!repository.idIsExists(id)){
            throw new ChargeNotFoundException("Charge not found by ID");
        }
        repository.deleteChargeById(id);
    }

    @Transactional
    @Modifying
    public void update(@NonNull BigDecimal amount, @NonNull LocalDate chargeDate, @NonNull Integer id, Integer id1){
        if(!repository.idIsExists(id1)){
            throw new ChargeNotFoundException("Charge not found by ID");
        }
        if(!repository.isExpItemIdExists(id)){
            throw new ExpenseItemNotFoundException("ExpenseItem not found");
        }
        repository.update(amount, chargeDate, id, id1);
    }

    public Charge findById(Integer id){
        Optional<Charge> charge = repository.findChargeById(id);
        if(charge.isPresent()){
            return charge.get();
        }
        else{
            throw new ChargeNotFoundException("Charge not found by ID");
        }
    }

    public Charge add(Charge charge){
        if(charge.getChargeDate() == null){
            throw new IllegalArgumentException("Charge Date can`t be null");
        }
        if(charge.getAmount() == null){
            throw new IllegalArgumentException("Charge Amount can`t be null");
        }
        if(charge.getExpanseItem() == null){
            throw new IllegalArgumentException("Charge Ex_Item_ID can`t be null");
        }
        if(!ex_repos.isExistsById(charge.getExpanseItem().getId())){
            throw new ExpenseItemNotFoundException("Expense item not found by ID");
        }
        return repository.save(charge);
    }

    public List<Charge> findAll(){return(List<Charge>) repository.findAll();}

    public List<Charge> findByExpanseId(Integer id){
        List<Charge> charges = repository.findByExpanseItemsId(id);
        if(!charges.isEmpty()){
            return(List<Charge>) charges;
        }
        else{
            throw new ChargeNotFoundException("Charge not found by ExpenseID");
        }
    }

    public List<Charge> findByExpanseName(String name){
        List<Charge> charges = repository.findByExpanseItemName(name);
        if(!charges.isEmpty()){
            return(List<Charge>) charges;
        }
        else{
            throw new ChargeNotFoundException("Charge not found by ExpenseName");
        }
    }

    public Optional<ExpenseItem> findExItemById(Integer id){return repository.findExItemById(id);}

    public boolean idIsExists(Integer id){return repository.idIsExists(id);}
    public boolean isExpItemNameExists(String name){return repository.isExpItemNameExists(name);}
    public boolean isExpItemIdExists(Integer id){return repository.isExpItemIdExists(id);}

    public List<Charge> findByAmount(BigDecimal amountStart, BigDecimal amountEnd){
        return repository.findByAmountBetween(amountStart, amountEnd);
    }

    public List<Charge> findByDate(LocalDate chargeDateStart, LocalDate chargeDateEnd){
        return repository.findByDateBetween(chargeDateStart, chargeDateEnd);
    }

}
