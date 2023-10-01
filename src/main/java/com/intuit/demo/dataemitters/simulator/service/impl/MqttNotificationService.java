package com.intuit.demo.dataemitters.simulator.service.impl;

import com.intuit.demo.dataemitters.simulator.service.NotificationService;
import com.intuit.demo.dataemitters.simulator.service.dto.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

@Service
@Slf4j
public class MqttNotificationService implements NotificationService<Vehicle> {

    @Value("${mqtt.topic}")
    private String topic;
    private final MqttClient mqttClient;

    public MqttNotificationService(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @Override
    public void publish(Vehicle message) throws MqttException {
        log.info("message notified to backend {}", message);

        MqttMessage mqttMessage = new MqttMessage(getBytes(message));
        mqttMessage.setQos(0);

        mqttClient.publish(topic, mqttMessage);
    }

    private byte[] getBytes(Vehicle vehicle) {
        byte[] ret = new byte[0];
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(vehicle);
            ret =  byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("exception on converting to bytes",e);
        }
        return ret;
    }
}
