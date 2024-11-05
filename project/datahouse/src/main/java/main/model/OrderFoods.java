package main.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class OrderFoods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String foodName;
    private String quantity;
    private String price;
}
