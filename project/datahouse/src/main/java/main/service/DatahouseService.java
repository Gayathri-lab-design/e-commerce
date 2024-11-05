package main.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import main.model.*;
import main.repository.DatahouseRepository;
import main.repository.FoodRepo;
import main.repository.LoginRepo;
import main.repository.OrderRepo;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DatahouseService {

    private DatahouseRepository datahouseRepository;
    ObjectMapper objectMapper = new ObjectMapper();
    private FoodRepo foodRepo;
    private LoginRepo loginRepo;
    private OrderRepo orderRepo;

    public DatahouseService(OrderRepo orderRepo, LoginRepo loginRepo, DatahouseRepository datahouseRepository, FoodRepo foodRepo){
        this.datahouseRepository = datahouseRepository;
        this.loginRepo = loginRepo;
        this.orderRepo = orderRepo;
        this.foodRepo = foodRepo;
    }
    
    public String addStudDetails(String data){
        try {
            JsonNode info = objectMapper.readTree(data);
            DatahouseModel datahouseModel = new DatahouseModel();
            datahouseModel.setDepartment(info.get("department").asText());
            datahouseModel.setBoys(info.get("boys").asInt());
            datahouseModel.setSection(info.get("section").asText());
            datahouseModel.setGirls(info.get("girls").asInt());
            datahouseRepository.save(datahouseModel);
            return "Added Successfully .";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public String deleteStudDetails(String data) {
        try {
            JsonNode info = objectMapper.readTree(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public ResponseModel getChartValues() {
        ObjectNode result = objectMapper.createObjectNode();
        ResponseModel responseModel = new ResponseModel();
        JSONObject object = new JSONObject();
        try {
            List<Integer> boys =  new ArrayList<>();
            List<Integer> girls =  new ArrayList<>();
            getASection(boys, girls);
            getBSection(boys, girls);
            getCSection(boys, girls);
            List<String> labels = new ArrayList<>();
            labels.add("SECTION A");
            labels.add("SECTION B");
            labels.add("SECTION C");

            responseModel.setLabels(labels);
            responseModel.setBoys(boys);
            responseModel.setGirls(girls);

        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return responseModel;
    }

    private void getASection(List<Integer> boy, List<Integer> girl) {
        List<DatahouseModel> aSec = datahouseRepository.findBySection("SECTION-A");
        Integer boys = 0;
        Integer girls = 0;
        for (DatahouseModel data : aSec){
            boys += data.getBoys();
            girls += data.getGirls();
        }
        boy.add(boys);
        girl.add(girls);
    }
    private void getBSection(List<Integer> boy, List<Integer> girl) {
        List<DatahouseModel> bSec = datahouseRepository.findBySection("SECTION-B");
        Integer boys = 0;
        Integer girls = 0;
        for (DatahouseModel data : bSec){
            boys += data.getBoys();
            girls += data.getGirls();
        }
        boy.add(boys);
        girl.add(girls);
    }

    private void getCSection(List<Integer> boy, List<Integer> girl) {
        List<DatahouseModel> cSec = datahouseRepository.findBySection("SECTION-C");
        Integer boys = 0;
        Integer girls = 0;
        for (DatahouseModel data : cSec){
            boys += data.getBoys();
            girls += data.getGirls();
        }
        boy.add(boys);
        girl.add(girls);
    }

    public List<FoodItems> getAllFoods() {
        List<FoodItems> foods = foodRepo.findAll();
        log.info(foods.toString());
            return foods;
    }

    public boolean loginCheck(String log) {
        boolean islog = false;
        try {
            JsonNode logs = objectMapper.readTree(log);
            List<LoginDetails> loginDetails = loginRepo.findByName(logs.get("name").asText());
            for (LoginDetails login : loginDetails){
                if (login.getName().equals(logs.get("name").asText())
                        && login.getPassword().equals(logs.get("password").asText())){
                    islog = true;
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return islog;
    }

    public String orderFood(String data) {
        try {
            JsonNode logs = objectMapper.readTree(data);
            OrderFoods orderFoods = new OrderFoods();
            orderFoods.setFoodName(logs.get("foodname").asText());
            orderFoods.setQuantity(logs.get("quantity").asText());
            orderFoods.setPrice(logs.get("price").asText());
            orderRepo.save(orderFoods);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "Added";
    }
}
