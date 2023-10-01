package com.intuit.demo.dataemitters.simulator.common.configs;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class MqttConfigs {

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.topic}")
    private String topic;

    @Bean("mqttClient")
    public MqttClient mqttClient() throws MqttException {
        MqttClient client = new MqttClient(brokerUrl, MqttClient.generateClientId());
        MqttConnectOptions options = new MqttConnectOptions();
        client.connect(options);
        return client;
    }
}
