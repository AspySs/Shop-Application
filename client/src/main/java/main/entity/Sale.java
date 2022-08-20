package main.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Sale {
    private Integer id;
    private BigDecimal quantity;
    private BigDecimal amount;
    private String saleDate;
    private Warehouse warehouse;

    @Override
    public String toString() {
        return "Charge{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", amount=" + amount +
                ", saleDate=" + saleDate +
                ", warehouse_id=" + warehouse.getId() +
                '}';
    }
}
