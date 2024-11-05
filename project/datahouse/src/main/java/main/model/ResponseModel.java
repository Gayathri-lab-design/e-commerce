package main.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseModel {

    private List<String> labels = new ArrayList<>();
    private List<Integer> boys = new ArrayList<>();
    private List<Integer> girls = new ArrayList<>();

}
