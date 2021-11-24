package com.marsrover.rover.domain.service.impl;

import com.marsrover.rover.cache.InputCache;
import com.marsrover.rover.domain.dao.RoverRepository;
import com.marsrover.rover.domain.entity.Coordinates;
import com.marsrover.rover.domain.entity.Rover;
import com.marsrover.rover.domain.service.RoverService;
import com.marsrover.rover.exception.BadRequestException;
import com.marsrover.rover.utils.InputUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class RoverServiceImpl implements RoverService {

    private final RoverRepository roverRepository;
    private final InputCache inputCache;

    public static final String MISSING_INPUT_INFORMATION = "Missing information. Please make sure you're providing all the necessary data";

    public String getResult(String rawInput) throws BadRequestException {
        Coordinates gridCoordinates;
        String input = rawInput.replace("\r", "");
        String output = inputCache.getValidOutput(input);
        if (output != null) {
            return output;
        }
        output = getOutputFromDB(input);
        if (output != null) {
            return output;
        }

        Integer numberOfRobots = InputUtils.getNumberOfRobots(input);
        gridCoordinates = InputUtils.getGridCoordinates(input.split("\n")[0]);
        if (numberOfRobots > 0) {
            List<Rover> roverList = new ArrayList<>();
            for (int i = 0; i < numberOfRobots; i++) {
                roverList = populateRoverList(input, numberOfRobots, gridCoordinates);
            }
            StringBuilder outputFormated = formOutput(roverList);
            inputCache.setOutput(outputFormated.toString(), 3, input);
            addResultToDB(input, outputFormated.toString());
            return outputFormated.toString();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, MISSING_INPUT_INFORMATION);
        }
    }

    @Override
    public int addResultToDB(String input, String output) {
        return roverRepository.addResult(input, output);
    }

    @Override
    public String getOutputFromDB(String input) {
        return roverRepository.getOutput(input);
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
