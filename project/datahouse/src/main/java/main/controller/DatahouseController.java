package main.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import main.model.FoodItems;
import main.model.ResponseModel;
import main.service.DatahouseService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1")
@CrossOrigin("*")
@Slf4j
public class DatahouseController {

    private DatahouseService datahouseService ;
    ObjectMapper objectMapper = new ObjectMapper();

    public DatahouseController(DatahouseService datahouseService){
        this.datahouseService = datahouseService;
    }

    @GetMapping("/ping")
    public String serviceUp(){
        return "Service Working.";
    }

    @PostMapping("/addStudDetails")
    public ResponseEntity<String> addStudDetails(@RequestBody String data){
       String res = datahouseService.addStudDetails(data);
       return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(res);
    }

    @GetMapping("/chartValues")
    public ResponseModel getChartValues(){
        return datahouseService.getChartValues();
    }

    @DeleteMapping("/deleteStudDetails")
    public String deleteStudDetails(@RequestBody String data){
        return datahouseService.deleteStudDetails(data);
    }

    @GetMapping("/foodItems")
    public List<FoodItems> getAllFoods(){
       return datahouseService.getAllFoods();
    }

    @PostMapping("/login")
    public boolean loginCheck(@RequestBody String log){
        return datahouseService.loginCheck(log);
    }

    @PostMapping("/order")
    public String orderFood(@RequestBody String data){
        return datahouseService.orderFood(data);
    }
}
