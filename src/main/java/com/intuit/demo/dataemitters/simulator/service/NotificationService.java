package com.intuit.demo.dataemitters.simulator.service;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface NotificationService<T> {

    void publish(T message) throws MqttException;
}
