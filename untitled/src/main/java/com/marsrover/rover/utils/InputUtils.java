package com.marsrover.rover.utils;

import com.marsrover.rover.domain.entity.Coordinates;
import com.marsrover.rover.exception.BadRequestException;


public class InputUtils {

    public static final String GRID_COORDINATES_REQUIREMENTS = "Grid coordinates can only be between 0 and 50";
    public static final String GRID_COORDINATES_LONGER_THAN_EXPECTED = "You must pass a pair of coordinates";
    public static final String GRID_COORDINATES_MATCHERS = "Grid coordinates can only contain numbers";
    public static final String INSTRUCTIONS_LENGTH = "Instructions cannot contain more than 100 characters";
    public static final String INSTRUCTIONS_MATCHERS = "Instructions can only contain 'F' 'R' or 'L' characters";
    public static final String ROVER_COORDINATES_LENGTH = "Rover coordinates can only contain be between 0 and the maximum value for the grid coordinates";
    public static final String ROVER_COORDINATES_MATCHERS = ROVER_COORDINATES_LENGTH + ". Rover original orientation can just contain 'N' 'S' 'E' or 'W' values";

    private static String validateGridCoordinates(String gridCoordinates) {
        String[] splitGridCoordinates = gridCoordinates.split(" ");
        if (splitGridCoordinates.length != 2) {
            return GRID_COORDINATES_LONGER_THAN_EXPECTED;
        }
        if (!gridCoordinates.matches("[0-9 ]+")) {
            return GRID_COORDINATES_MATCHERS;
        }
        int firstCoordinate = Integer.parseInt(splitGridCoordinates[0]);
        int secondCoordinate = Integer.parseInt(splitGridCoordinates[1]);
        if (firstCoordinate < 0 || firstCoordinate > 50 || secondCoordinate < 0 || secondCoordinate > 50) {
            return GRID_COORDINATES_REQUIREMENTS;
        }
        return "";
    }

    public static Coordinates getGridCoordinates(String input) throws BadRequestException {
        String validateGridCoordinates = validateGridCoordinates(input);
        if (validateGridCoordinates.isEmpty()) {
            String[] splitGridCoordinates = input.split(" ");
            return Coordinates.builder()
                    .coordinateX(Integer.parseInt(splitGridCoordinates[0]))
                    .coordinateY(Integer.parseInt(splitGridCoordinates[1]))
                    .build();
        } else {
            throw new BadRequestException(validateGridCoordinates);
        }
    }

    private static String validateInstructions(String instructions) {
        if (instructions.length() > 100) {
            return INSTRUCTIONS_LENGTH;
        } else if (!instructions.matches("[LFR]+")) {
            return INSTRUCTIONS_MATCHERS;
        }
        return "";
    }

    public static String getInstructions(String instructions) throws BadRequestException {
        String validateInstructions = validateInstructions(instructions);
        if (validateInstructions.isEmpty()) {
            return instructions;
        } else {
            throw new BadRequestException(validateInstructions);
        }
    }

    private static String validateRoverCoordinates(String[] roverCoordinates, Coordinates gridCoordinates) {
        if (!roverCoordinates[0].matches("[0-9]+")
                || !roverCoordinates[1].matches("[0-9]+")
                || !roverCoordinates[2].matches("[NSEW]+")) {
            return ROVER_COORDINATES_MATCHERS;
        } else if (Integer.parseInt(roverCoordinates[0]) < 0
                || Integer.parseInt(roverCoordinates[1]) < 0
                || Integer.parseInt(roverCoordinates[0]) > gridCoordinates.getCoordinateX()
                || Integer.parseInt(roverCoordinates[1]) > gridCoordinates.getCoordinateY()) {
            return ROVER_COORDINATES_LENGTH;
        }
        return "";
    }

    public static Coordinates getRoverCoordinates(String[] roverCoordinates, Coordinates gridCoordinates) throws BadRequestException {
        String validateRoverCoordinates = validateRoverCoordinates(roverCoordinates, gridCoordinates);
        if (validateRoverCoordinates.isEmpty()) {
            return Coordinates.builder()
                    .coordinateX(Integer.parseInt(roverCoordinates[0]))
                    .coordinateY(Integer.parseInt(roverCoordinates[1]))
                    .build();
        } else {
            throw new BadRequestException(validateRoverCoordinates);
        }
    }

    public static Integer getNumberOfRobots(String str) {
        String[] lines = str.split("\n");
        return (lines.length - 1) / 2;
    }
}
