package com.practo.practo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PractoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PractoApplication.class, args);
	}

	//	@Bean
//	public CommandLineRunner init(){
//		return args -> {
//			//Load your variable or perform initialization here
//			//to load it only once the project starts
//			List<String> availableTimeSloats = new ArrayList<>();
//			availableTimeSloats.add("10:50 AM");
//			availableTimeSloats.add("11:15 AM");
//			availableTimeSloats.add("12:15 AM");
//			//you can store 'availableTimeSlots' list or use it as needed
//		};
//	}
}
