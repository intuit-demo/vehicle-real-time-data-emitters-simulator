package com.intuit.demo.dataemitters.simulator.service.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Vehicle implements Serializable {

    private String registrationNumber;
    private String status;
    private Double latitude;
    private Double longitude;
    private Double speed;
    private Double fuel;
}
