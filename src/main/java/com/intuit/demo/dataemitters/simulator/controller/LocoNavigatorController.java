package com.intuit.demo.dataemitters.simulator.controller;

import com.intuit.demo.dataemitters.simulator.controller.model.RegisteredVehicle;
import com.intuit.demo.dataemitters.simulator.service.RegiseredVehicle;
import com.intuit.demo.dataemitters.simulator.service.dto.VehicleState;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/v1/locoNavigator")
public class LocoNavigatorController {

    private final RegiseredVehicle regiseredVehicle;

    public LocoNavigatorController(RegiseredVehicle regiseredVehicle) {
        this.regiseredVehicle = regiseredVehicle;
    }

    @PostMapping(value= "/ignitionOn", produces = MediaType.APPLICATION_JSON_VALUE)
    public void ignitionOn(@RequestBody RegisteredVehicle vehicleRegister) {
        log.info("request received with status ignitionOn vehicle {}", vehicleRegister);
        regiseredVehicle.ignitionOn(vehicleRegister.getRegistrationNumber());
    }

    @PostMapping(value= "/ignitionOff", produces = MediaType.APPLICATION_JSON_VALUE)
    public void ignitionOff(@RequestBody RegisteredVehicle vehicleRegister) {
        log.info("request received with status ignitionOff vehicle {}", vehicleRegister);
        regiseredVehicle.ignitionOff(vehicleRegister.getRegistrationNumber());
    }

    @Validated
    @PostMapping(value= "/accelerate", produces = MediaType.APPLICATION_JSON_VALUE)
    public void accelerate(@RequestBody @NonNull RegisteredVehicle vehicleRegister) {
        log.info("request received with status accelerate vehicle {}", vehicleRegister);
        if(VehicleState.PRESS_ACCELERATE.equals(vehicleRegister.getVehicleState())) {
            regiseredVehicle.accelerate(vehicleRegister.getRegistrationNumber());
        } else {
            regiseredVehicle.slowDown(vehicleRegister.getRegistrationNumber());
        }
    }
}
