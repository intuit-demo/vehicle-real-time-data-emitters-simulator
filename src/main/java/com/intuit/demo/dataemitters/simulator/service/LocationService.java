package com.intuit.demo.dataemitters.simulator.service;

import reactor.util.function.Tuple2;

public interface LocationService<LA, LO> {

    Tuple2<LA, LO> getCurrentLocation();

    Tuple2<LA, LO> getCurrentLocation(Tuple2<LA, LO> latLong);
}
