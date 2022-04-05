package service;

import entity.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ChargeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ChargeService {
    @Autowired
    private final ChargeRepository repository;

    @Autowired
    public ChargeService(ChargeRepository repository){this.repository = repository;}

    public void deleteById(Integer id){
        repository.deleteChargeById(id);
    }

    public Optional<Charge> findById(Integer id){
        return repository.findChargeById(id);
    }

    public Charge save(Charge charge){
        return repository.save(charge);
    }

    public Iterable<Charge> findAll(){
        return repository.findAll();
    }

    public Optional<Charge> findByExpanseId(Integer id){
        return repository.findByExpanseItemsId(id);
    }

    public Optional<Charge> findByExpanseName(String name){
        return repository.findByExpanseItemName(name);
    }

    public List<Charge> findByAmount(BigDecimal amountStart, BigDecimal amountEnd){
        return repository.findByAmountBetween(amountStart, amountEnd);
    }

    public List<Charge> findByDate(LocalDate chargeDateStart, LocalDate chargeDateEnd){
        return repository.findByDateBetween(chargeDateStart, chargeDateEnd);
    }

}
