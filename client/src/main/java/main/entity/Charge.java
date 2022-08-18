package main.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class Charge {

    private Integer id;
    private BigDecimal amount;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String chargeDate;

    private ExpenseItem expanseItem;

    @Override
    public String toString() {
        return "Charge{" +
                "id=" + id +
                ", amount=" + amount +
                ", chargeDate=" + chargeDate +
                ", expanseItem_ID=" + expanseItem.getId() +
                '}';
    }

}
