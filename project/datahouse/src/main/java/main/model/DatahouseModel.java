package main.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "data_house")
@Data
public class DatahouseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sNo;

    private String department;
    private String section;
    private Integer boys;
    private Integer girls;
}
