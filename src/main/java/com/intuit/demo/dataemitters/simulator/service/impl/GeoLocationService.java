package com.intuit.demo.dataemitters.simulator.service.impl;

import com.intuit.demo.dataemitters.simulator.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Random;

@Service
@Slf4j
public class GeoLocationService implements LocationService<Double, Double> {

    @Override
    public Tuple2<Double, Double> getCurrentLocation() {
        double multiplier = 10000L;
        double latitude=(Math.random()*(90*multiplier))/multiplier;
        double longitude=(Math.random()*(180*multiplier))/multiplier;
        latitude *=(Math.floor(Math.random()*2) == 1)?1:-1;
        longitude *=(Math.floor(Math.random()*2) == 1)?1:-1;
        return Tuples.of(latitude, longitude);
    }

    @Override
    public Tuple2<Double, Double> getCurrentLocation(Tuple2<Double, Double> latLong) {
        double latitude = latLong.getT1();
        double longitude = latLong.getT2();

        for (; ; ) {
            double latitudeChange = getRandomDoubleInRange(-0.01, 0.01); // Adjust the range as needed
            double longitudeChange = getRandomDoubleInRange(-0.01, 0.01); // Adjust the range as needed
            latitude += latitudeChange;
            longitude += longitudeChange;

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Tuples.of(latitude, longitude);
            }
        }
    private  double getRandomDoubleInRange(double min, double max) {
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }
}
