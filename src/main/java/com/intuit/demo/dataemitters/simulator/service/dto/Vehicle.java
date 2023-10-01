package com.intuit.demo.dataemitters.simulator.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Vehicle {

    private String registrationNumber;
    private String status;
    private Double latitude;
    private Double longitude;
    private Double speed;
    private Double fuel;
}
