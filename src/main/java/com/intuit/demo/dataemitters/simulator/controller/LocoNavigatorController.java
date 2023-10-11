package com.intuit.demo.dataemitters.simulator.controller;

import com.intuit.demo.dataemitters.simulator.service.RegisteredVehicleService;
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

    private final RegisteredVehicleService registeredVehicleService;

    public LocoNavigatorController(RegisteredVehicleService registeredVehicleService) {
        this.registeredVehicleService = registeredVehicleService;
    }

    @PostMapping(value = "/ignitionOn", produces = MediaType.APPLICATION_JSON_VALUE)
    public void ignitionOn(@RequestBody com.intuit.demo.dataemitters.simulator.controller.model.RegisteredVehicle vehicleRegister) {
        log.info("request received with status ignitionOn vehicle {}", vehicleRegister);
        registeredVehicleService.ignitionOn(vehicleRegister.getRegistrationNumber());
    }

    @PostMapping(value = "/ignitionOff", produces = MediaType.APPLICATION_JSON_VALUE)
    public void ignitionOff(@RequestBody com.intuit.demo.dataemitters.simulator.controller.model.RegisteredVehicle vehicleRegister) {
        log.info("request received with status ignitionOff vehicle {}", vehicleRegister);
        registeredVehicleService.ignitionOff(vehicleRegister.getRegistrationNumber());
    }

    @Validated
    @PostMapping(value = "/accelerate", produces = MediaType.APPLICATION_JSON_VALUE)
    public void accelerate(@RequestBody @NonNull com.intuit.demo.dataemitters.simulator.controller.model.RegisteredVehicle vehicleRegister) {
        log.info("request received with status accelerate vehicle {}", vehicleRegister);
        if (VehicleState.PRESS_ACCELERATE.equals(vehicleRegister.getVehicleState())) {
            registeredVehicleService.accelerate(vehicleRegister.getRegistrationNumber());
        } else {
            registeredVehicleService.slowDown(vehicleRegister.getRegistrationNumber());
        }
    }
}
