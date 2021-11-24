package com.marsrover.rover.controller.impl;

import com.marsrover.rover.controller.RoverApi;
import com.marsrover.rover.domain.service.RoverService;
import com.marsrover.rover.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Slf4j
public class RoverController implements RoverApi {


    private final RoverService roverService;

    public ResponseEntity<String> getCoordinates(String rawInput) {
        try {
            return new ResponseEntity<>(roverService.getResult(rawInput), HttpStatus.OK);
        } catch (BadRequestException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        }
    }

}
