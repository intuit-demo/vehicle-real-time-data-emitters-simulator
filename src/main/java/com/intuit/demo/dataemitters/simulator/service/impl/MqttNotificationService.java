package com.intuit.demo.dataemitters.simulator.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.demo.dataemitters.simulator.service.NotificationService;
import com.intuit.demo.dataemitters.simulator.service.dto.VehicleRealTimeEvent;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MqttNotificationService implements NotificationService<VehicleRealTimeEvent> {

    private final MqttClient mqttClient;
    @Value("${mqtt.topic}")
    private String topic;
    private ObjectMapper objectMapper = new ObjectMapper();

    public MqttNotificationService(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @Override
    public void publish(VehicleRealTimeEvent message) throws MqttException, JsonProcessingException {
        log.info("message notified to backend {}", message);

        MqttMessage mqttMessage = new MqttMessage(getBytes(message));
        mqttMessage.setQos(0);
        mqttMessage.setRetained(true);
        mqttClient.publish(topic, mqttMessage);
    }

    private byte[] getBytes(VehicleRealTimeEvent vehicle) throws JsonProcessingException {
        return objectMapper.writeValueAsBytes(vehicle);
    }
}
