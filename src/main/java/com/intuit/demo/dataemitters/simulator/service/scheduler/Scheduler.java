package com.intuit.demo.dataemitters.simulator.service.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Slf4j
@Service
public class Scheduler {

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    private ConcurrentHashMap<String, ScheduledFuture> threadSafeMap = new ConcurrentHashMap<>();

    public boolean startScheduledExecutorService(String key, Runnable task) {

        if (threadSafeMap.containsKey(key)) {
            //log.info("previously registered scheduler");
            return false;
        } else {
            //log.info("newly registered scheduler");
            threadSafeMap.computeIfAbsent(key, (k) -> scheduledExecutorService.scheduleWithFixedDelay(task, 0, 1, TimeUnit.DAYS));
            return true;
        }
    }

    public void stopScheduledExecutorService(String key) {
        if (threadSafeMap.containsKey(key)) {
            var s = threadSafeMap.get(key);
            s.cancel(true);
            threadSafeMap.remove(key);
        }
    }
}
