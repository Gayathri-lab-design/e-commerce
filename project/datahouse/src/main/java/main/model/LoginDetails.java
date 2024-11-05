package main.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table
public class LoginDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name ;
    private String password;
}
