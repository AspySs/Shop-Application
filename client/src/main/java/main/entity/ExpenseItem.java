package main.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseItem {
    private Integer id;
    private String name;

    @Override
    public String toString() {
        return "Charge{" +
                "id=" + id +
                ", name=" + "\"" + name + "\"" +
                '}';
    }
}
