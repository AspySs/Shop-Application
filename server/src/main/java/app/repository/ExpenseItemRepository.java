package app.repository;

import app.entity.ExpenseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ExpenseItemRepository extends JpaRepository<ExpenseItem, Integer> {
    @Transactional
    @Modifying
    @Query("update ExpenseItem e set e.name = ?1 where e.id = ?2")
    int updateNameByIdEquals(@NonNull String name, Integer id);
    @Query("select e from ExpenseItem e where e.name = ?1")
    List<ExpenseItem> findByName(String name);
    @Query("select (count(e) > 0) from ExpenseItem e where e.name = ?1")
    boolean isExistsByName(String name);
    @Query("select (count(e) > 0) from ExpenseItem e where e.id = ?1")
    boolean isExistsById(Integer id);
}