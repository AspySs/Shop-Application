package entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "expense_items")
@Entity
@Getter
@Setter
public class ExpenseItem {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;
}