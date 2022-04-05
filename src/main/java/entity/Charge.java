package entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "charges")
@Entity
@Getter
@Setter
public class Charge {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "amount", precision = 18, scale = 2)
    private BigDecimal amount;

    @Convert(disableConversion = true)
    @Column(name = "charge_date")
    private LocalDate chargeDate;

    @ManyToOne
    @JoinColumn(name = "expanse_item_id")
    private ExpenseItem expanseItem;

}