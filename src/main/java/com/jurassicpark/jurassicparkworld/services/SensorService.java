package com.jurassicpark.jurassicparkworld.services;


import com.jurassicpark.jurassicparkworld.models.Sensor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;

@Service
public class SensorService {

    private final List<Sensor> sensors;

    public SensorService(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public Flux<Sensor> getSensorDataStream() {
        return Flux.fromIterable(sensors)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(sensor -> System.out.println("Emitting data for: " + sensor.getName()))
                .onBackpressureBuffer(10,
                        dropped -> System.out.println("Dropped sensor data: " + dropped.getName()))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> processSensorData() {
        return getSensorDataStream()
                .parallel()
                .runOn(Schedulers.parallel())
                .doOnNext(sensor -> System.out.println("Processing data for: " + sensor.getName()))
                .then();
    }
}
