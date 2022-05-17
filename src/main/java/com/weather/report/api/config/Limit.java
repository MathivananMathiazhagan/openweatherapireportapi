package com.weather.report.api.config;

import java.time.Duration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;

public enum Limit {

    LIMIT(5);

    private int bucketCapacity;
    
    private Limit(int bucketCapacity) {
        this.bucketCapacity = bucketCapacity;
    }
    
    Bandwidth getLimit() {
        return Bandwidth.classic(bucketCapacity, Refill.intervally(bucketCapacity, Duration.ofHours(1)));
    }
    
    public int bucketCapacity() {
        return bucketCapacity;
    }
    
    static Limit resolvePlanFromApiKey(String apiKey) {
         
        return LIMIT;
    }
}