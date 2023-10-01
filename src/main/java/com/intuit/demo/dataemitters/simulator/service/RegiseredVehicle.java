package com.intuit.demo.dataemitters.simulator.service;

public interface RegiseredVehicle {

    void ignitionOn(String registrationNumber);

    void ignitionOff(String registrationNumber);

    void accelerate(String registrationNumber);

    void slowDown(String registrationNumber);
}
