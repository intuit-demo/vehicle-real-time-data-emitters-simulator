package com.intuit.demo.dataemitters.simulator.controller.model;

import com.intuit.demo.dataemitters.simulator.service.dto.VehicleState;
import lombok.Data;
import reactor.util.annotation.Nullable;

@Data
public class RegisteredVehicle {

    private String registrationNumber;

    @Nullable
    private VehicleState vehicleState;
}
