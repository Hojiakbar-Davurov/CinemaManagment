package com.example.cinemamanagment.repository;


import com.example.cinemamanagment.model.domain.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTypeRepository extends JpaRepository<OrderType, Long> {
    boolean existsByName(String name);
}
