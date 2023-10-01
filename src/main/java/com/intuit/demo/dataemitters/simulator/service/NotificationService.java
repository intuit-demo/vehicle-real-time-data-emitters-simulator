package com.intuit.demo.dataemitters.simulator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.MqttException;

public interface NotificationService<T> {

    void publish(T message) throws MqttException, JsonProcessingException;
}
