package com.marsrover.rover.config;

import com.marsrover.rover.RoverControllerRestService;
import com.marsrover.rover.cache.InputCache;
import com.marsrover.rover.controller.RoverController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarsRoverConfig {

    @Bean
    public InputCache jwtCache() {
        return new InputCache();
    }

    @Bean
    public RoverController roverController(InputCache inputCache) {
        return new RoverControllerRestService(inputCache);
    }
}
