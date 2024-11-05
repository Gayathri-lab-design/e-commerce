package main.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table
@Entity
public class FoodItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String image;
    private String price;
}
