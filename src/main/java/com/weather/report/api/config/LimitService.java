package com.weather.report.api.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;

@Service
public class LimitService {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String apiKey) {
        return cache.computeIfAbsent(apiKey, this::newBucket);
    }

    private Bucket newBucket(String apiKey) {
        Limit pricingPlan = Limit.resolvePlanFromApiKey(apiKey);
        return bucket(pricingPlan.getLimit());
    }

    private Bucket bucket(Bandwidth limit) {
        return Bucket4j.builder()
            .addLimit(limit)
            .build();
    }
}