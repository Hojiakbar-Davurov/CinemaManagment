package com.example.cinemamanagment;

import com.example.cinemamanagment.service.OrderTypeService;
import com.example.cinemamanagment.service.SeatStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CinemaManagementApplication {
    @Autowired
    private SeatStatusService seatStatusService;
    @Autowired
    private OrderTypeService orderTypeService;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl_auto;

    public static void main(String[] args) {
        SpringApplication.run(CinemaManagementApplication.class, args);
    }
    @Bean
    public CommandLineRunner CommandLineRunnerBean() {
        return (args) -> {
            if (ddl_auto.equals("create")) {
                seatStatusService.autoSave();
                orderTypeService.autoSave();
                System.out.println("\n***************   All enums inserted to DB automatically   ****************\n");
            }
        };
    }
}
