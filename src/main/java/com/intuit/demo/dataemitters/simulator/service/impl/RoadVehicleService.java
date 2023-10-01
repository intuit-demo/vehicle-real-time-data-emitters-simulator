package com.intuit.demo.dataemitters.simulator.service.impl;

import com.intuit.demo.dataemitters.simulator.service.LocationService;
import com.intuit.demo.dataemitters.simulator.service.RegiseredVehicle;
import com.intuit.demo.dataemitters.simulator.service.dto.Vehicle;
import com.intuit.demo.dataemitters.simulator.service.dto.VehicleState;
import com.intuit.demo.dataemitters.simulator.service.scheduler.Scheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class RoadVehicleService implements RegiseredVehicle {

    private final Scheduler scheduler;
    private final LocationService<Double, Double> locationService;
    private ConcurrentHashMap<String, Vehicle> threadSafeMap = new ConcurrentHashMap<>();

    public RoadVehicleService(Scheduler scheduler, LocationService locationService) {
        this.scheduler = scheduler;
        this.locationService = locationService;
    }

    @Override
    public void ignitionOn(String registrationNumber) {
        var t = locationService.getCurrentLocation();
        var v = Vehicle.builder()
                .fuel(100.00)
                .speed(20.00)
                .latitude(t.getT1())
                .longitude(t.getT2())
                .registrationNumber(registrationNumber)
                .status(VehicleState.IGNITION_ON.name())
                .build();
        threadSafeMap.computeIfAbsent(registrationNumber, (k) -> v);
        log.info("total vehicles running {}", threadSafeMap.size());
        if(scheduler.startScheduledExecutorService(registrationNumber, () -> execute(registrationNumber))) {
            log.info("vehicle state :: {} {}", v.getStatus(), v);
        }
    }

    @Override
    public void ignitionOff(String registrationNumber) {

        if(threadSafeMap.containsKey(registrationNumber)) {
            scheduler.stopScheduledExecutorService(registrationNumber);
            var v = threadSafeMap.get(registrationNumber);
            v.setStatus(VehicleState.IGNITION_OFF.name());
            log.info("vehicle state :: {} {}", threadSafeMap.get(registrationNumber).getStatus(), threadSafeMap.get(registrationNumber));
            threadSafeMap.remove(registrationNumber);
        }
        log.info("total vehicles running {}", threadSafeMap.size());
    }

    @Override
    public void accelerate(String registrationNumber) {
        if(threadSafeMap.containsKey(registrationNumber)) {
            var v = threadSafeMap.get(registrationNumber);
            v.setStatus(VehicleState.PRESS_ACCELERATE.name());
        } else {
            log.info("Vehicle is not running ....");
        }
    }

    @Override
    public void slowDown(String registrationNumber) {
        if(threadSafeMap.containsKey(registrationNumber)) {
            var v = threadSafeMap.get(registrationNumber);
            v.setStatus(VehicleState.RELEASE_ACCELERATE.name());
        } else {
            log.info("Vehicle is not running ....");
        }
    }

    private void execute(String registrationNumber) {

        if(threadSafeMap.containsKey(registrationNumber)) {
            var vehicle = threadSafeMap.get(registrationNumber);
            vehicle.setStatus(VehicleState.RUNNING.name());

            for(;;) {
                try {
                    if(threadSafeMap.containsKey(registrationNumber)) {

                        vehicle = threadSafeMap.get(registrationNumber);

                        var tp =  locationService.getCurrentLocation(Tuples.of(vehicle.getLatitude(), vehicle.getLongitude()));

                        vehicle.setLatitude(tp.getT1());
                        vehicle.setLongitude(tp.getT2());

                        if(vehicle.getFuel() < 0) {
                            vehicle.setStatus(VehicleState.BREAKDOWN.name());
                        }
                        if(vehicle.getSpeed() < 0) {
                            vehicle.setStatus(VehicleState.PARKING.name());
                        }
                        if(vehicle.getStatus().equals(VehicleState.PRESS_ACCELERATE.name())) {
                            vehicle.setSpeed(vehicle.getSpeed() + 1.00);
                            if(vehicle.getFuel() > 0) {
                                vehicle.setFuel(vehicle.getFuel() - 0.5);
                            }
                        } else if(vehicle.getStatus().equals(VehicleState.RELEASE_ACCELERATE.name())) {
                            vehicle.setSpeed(vehicle.getSpeed() - 1.00);
                            if(vehicle.getFuel() > 0) {
                                vehicle.setFuel(vehicle.getFuel() - 0.2);
                            }
                        } else {
                            if(vehicle.getFuel() > 0) {
                                vehicle.setFuel(vehicle.getFuel() - 0.1);
                            }
                        }
                        log.info("vehicle state :: {} {}", threadSafeMap.get(registrationNumber).getStatus(), threadSafeMap.get(registrationNumber));
                        Thread.sleep(5000);
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    vehicle.setStatus(VehicleState.PARKING.name());
                    log.info("vehicle state :: {} {}", threadSafeMap.get(registrationNumber).getStatus(), threadSafeMap.get(registrationNumber));
                }
            }
        } else {
            log.info("Vehicle {} still stopping .... ", registrationNumber);
        }
    }
}
