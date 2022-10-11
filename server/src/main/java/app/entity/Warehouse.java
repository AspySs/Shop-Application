package app.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "warehouses")
@Entity
@Getter
@Setter
public class Warehouse {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "amount", precision = 18, scale = 2, nullable = false)
    private BigDecimal amount;

}