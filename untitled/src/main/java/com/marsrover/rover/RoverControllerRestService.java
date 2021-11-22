package com.marsrover.rover;

import com.marsrover.rover.cache.InputCache;
import com.marsrover.rover.controller.RoverController;
import com.marsrover.rover.error.BadRequestException;
import com.marsrover.rover.rover.Coordinates;
import com.marsrover.rover.rover.Rover;
import com.marsrover.rover.utils.InputUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Slf4j
public class RoverControllerRestService implements RoverController {

    private InputCache inputCache;

    public static final String MISSING_INPUT_INFORMATION = "Missing information. Please make sure you're providing all the necessary data";

    public ResponseEntity<String> getCoordinates(String input) throws BadRequestException {
        String outputFromCache = inputCache.getValidOutput(input);
        if (outputFromCache != null) {
            return new ResponseEntity<>(outputFromCache, HttpStatus.OK);
        }
        Integer numberOfRobots = InputUtils.getNumberOfRobots(input);
        Coordinates gridCoordinates = InputUtils.getGridCoordinates(input.split("\n")[0]);
        if (numberOfRobots > 0) {
            List<Rover> roverList = new ArrayList<>();
            for (int i = 0; i < numberOfRobots; i++) {
                roverList = populateRoverList(input, numberOfRobots, gridCoordinates);
            }
            StringBuilder output = formOutput(roverList);
            inputCache.setOutput(output.toString(), 3600, input);
            return new ResponseEntity<>(output.toString(), HttpStatus.OK);
        } else {
            throw new BadRequestException(MISSING_INPUT_INFORMATION);
        }
    }

    private StringBuilder formOutput(List<Rover> roverList) {
        StringBuilder output = new StringBuilder();
        Map<Coordinates, String> scent = new HashMap<>();
        for (Rover rover : roverList) {
            output.append(rover.getFinalPosition(scent));
            output.append("\n");
            if (rover.getDropOffPosition() != null) {
                scent.put(rover.getDropOffPosition(), rover.getOrientation());
            }
        }
        return output;
    }

    private List<Rover> populateRoverList(String input, int numberOfRobots, Coordinates gridCoordinates) throws BadRequestException {
        List<Rover> roverList = new ArrayList<>();
        final String[] splitInput = input.split("\n");
        for (int i = 0; i < numberOfRobots; i++) {
            Coordinates roverStartCoordinates = InputUtils.getRoverCoordinates(splitInput[2 * i + 1].toUpperCase().split(" "), gridCoordinates);
            Rover rover = Rover.builder()
                    .gridCordinates(gridCoordinates)
                    .coordinates(roverStartCoordinates)
                    .orientation(splitInput[2 * i + 1].split(" ")[2])
                    .instructions(InputUtils.getInstructions(splitInput[2 * i + 2].toUpperCase()))
                    .build();
            roverList.add(rover);
        }
        return roverList;
    }

}
