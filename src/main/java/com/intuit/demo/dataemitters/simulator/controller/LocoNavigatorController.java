package com.intuit.demo.dataemitters.simulator.controller;

import com.intuit.demo.dataemitters.simulator.controller.model.VehicleRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/v1/locoNavigator")
public class LocoNavigatorController {

    @PostMapping("/ignitionOn")
    public void ignitionOn(@RequestBody VehicleRegister vehicleRegister) {
        log.info("request received with status ignitionOn vehicle {}", vehicleRegister);
    }
}
