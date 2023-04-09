package com.turkcellcamp.rentacar.entities;

import com.turkcellcamp.rentacar.entities.enums.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "model_year")
    private int modelYear;

    @Column(name = "plate")
    private String plate;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @Column(name = "daily_price")
    private double dailyPrice;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @OneToMany(mappedBy = "car")
    private List<Maintenance> maintenanceList;
}
