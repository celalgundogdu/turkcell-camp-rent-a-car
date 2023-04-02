package com.turkcellcamp.rentacar.repository;

import com.turkcellcamp.rentacar.entities.Car;
import com.turkcellcamp.rentacar.entities.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {

    boolean existsByPlateIgnoreCase(String plate);

    List<Car> findAllByState(State state);
}
