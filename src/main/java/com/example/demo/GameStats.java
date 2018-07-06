package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
@Data
@Entity
public class GameStats {
    private @Id @GeneratedValue Integer id;
    private Double height;
    private Double speed;
    private Double fuel;
    private Double fuelConsumption;
    private Integer currentMove;
    @JsonIgnore private int finished;

    GameStats(){
        height = 100.0;
        speed = 0.0;
        fuel = 140.0;
        fuelConsumption = 0.0;
        currentMove = 0;
        finished = 0;
    }
}
