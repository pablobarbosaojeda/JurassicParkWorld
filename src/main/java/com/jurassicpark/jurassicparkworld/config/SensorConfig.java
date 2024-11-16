package com.jurassicpark.jurassicparkworld.config;


import com.jurassicpark.jurassicparkworld.models.Sensor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SensorConfig {

    @Bean
    public Sensor temperatureSensor() {
        return new Sensor("Temperature", "Measures the ambient temperature of the park.");
    }

    @Bean
    public Sensor humiditySensor() {
        return new Sensor("Humidity", "Monitors humidity levels for optimal dinosaur habitat conditions.");
    }

    @Bean
    public Sensor motionSensor() {
        return new Sensor("Motion", "Detects unusual movements in restricted areas.");
    }
}
