package com.marsrover.rover.config;

import com.marsrover.rover.controller.impl.RoverController;
import com.marsrover.rover.cache.InputCache;
import com.marsrover.rover.controller.RoverApi;
import com.marsrover.rover.domain.dao.RoverRepository;
import com.marsrover.rover.domain.dao.impl.RoverRepositoryImpl;
import com.marsrover.rover.domain.service.RoverService;
import com.marsrover.rover.domain.service.impl.RoverServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ComponentScan("com.marsrover.rover")
public class MarsRoverConfig {

    @Bean
    public RoverService roverService(RoverRepository roverRepository, InputCache inputCache){
        return new RoverServiceImpl(roverRepository, inputCache);
    }

    @Bean
    public RoverRepository roverRepository(JdbcTemplate jdbcTemplate){
        return new RoverRepositoryImpl(jdbcTemplate);
    }

    @Bean
    public InputCache jwtCache() {
        return new InputCache();
    }

    @Bean
    public RoverApi roverController(RoverService roverService) {
        return new RoverController(roverService);
    }
}
