package com.marsrover.rover.cache;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class InputCache {

    private final Map<String, ImmutablePair<String, Date>> cache = new HashMap<>();

    public InputCache() {}

    public String getValidOutput(String input) {
        ImmutablePair<String, Date> outputWithExpirationDate = cache.get(input);
        if (outputWithExpirationDate != null) {
            String output = outputWithExpirationDate.getLeft();
            Date expirationDate = outputWithExpirationDate.getRight();
            if (output != null && expirationDate != null && !Instant.now().isAfter(expirationDate.toInstant())) {
                return output;
            }
        }
        return null;
    }

    public synchronized void setOutput(String output, Integer expirationTime, String input) {
        Date expiration = new Date(System.currentTimeMillis() + expirationTime * 1000);
        ImmutablePair<String, Date> cacheOutput = new ImmutablePair<>(output, expiration);
        cache.put(input, cacheOutput);
    }

}
