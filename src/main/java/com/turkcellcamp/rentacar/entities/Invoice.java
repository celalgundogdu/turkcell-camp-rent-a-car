package com.turkcellcamp.rentacar.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String cardHolder;

    private String plate;

    private String modelName;

    private String brandName;

    private int modelYear;

    private double dailyPrice;

    private double totalPrice;

    private int rentedForDays;

    private LocalDateTime rentedAt;
}
