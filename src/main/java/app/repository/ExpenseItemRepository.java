package app.repository;

import app.entity.ExpenseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExpenseItemRepository extends JpaRepository<ExpenseItem, Integer> {
    @Query("select (count(e) > 0) from ExpenseItem e where e.name = ?1")
    boolean isExistsByName(String name);
    @Query("select (count(e) > 0) from ExpenseItem e where e.id = ?1")
    boolean isExistsById(Integer id);
    @Query("select e from ExpenseItem e where e.name = ?1")
    Optional<ExpenseItem> findByName(String name);
}