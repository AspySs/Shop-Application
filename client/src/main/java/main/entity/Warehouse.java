package main.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Warehouse {
    private Integer id;
    private String name;
    private BigDecimal quantity;
    private BigDecimal amount;

    @Override
    public String toString() {
        return "Charge{" +
                "id=" + id +
                ", name=" + "\"" + amount + "\"" +
                ", quantity=" + quantity +
                ", amount=" + amount +
                '}';
    }
}
