package com.intuit.demo.dataemitters.simulator.service;

public interface VehicleEvents {
    void ignitionOn();

    void ignitionOff();

    void play();

    void pause();

    void getFuelStatus();

    void getSpeedStatus();
}
