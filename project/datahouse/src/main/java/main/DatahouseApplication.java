package main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class DatahouseApplication {

	public static void main(String[] args) {

		SpringApplication.run(DatahouseApplication.class, args);
		log.info("Application Started SuccessFully..");
	}

}
