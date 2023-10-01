package com.intuit.demo.dataemitters.simulator.common.configs;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.demo.dataemitters.simulator.service.dto.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Configuration
@Component
@Slf4j
public class MqttConfigs {

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.topic}")
    private String topic;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Bean("mqttClient")
    public MqttClient mqttClient() throws MqttException {
        MqttClient client = new MqttClient(brokerUrl, "test-1");
        MqttConnectOptions options = new MqttConnectOptions();
        options.setMaxInflight(1000);
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                log.error("exception at connectionLost", throwable);
                try {
                    client.connect(options);
                } catch (MqttException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                log.info("message messageArrived {}", s);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                try {
                    log.info("message deliveryComplete {}", objectMapper.readValue(iMqttDeliveryToken.getMessage().getPayload(), Vehicle.class));
                } catch (Exception e) {
                    log.error("exception at deliveryComplete ", e);
                }
            }
        });
        client.connect(options);
        return client;
    }
}
