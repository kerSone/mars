package com.marsrover.rover.domain.service;

import com.marsrover.rover.exception.BadRequestException;

public interface RoverService {

    String getResult(String input) throws BadRequestException;

    int addResultToDB(String input, String output);

    String getOutputFromDB(String input);
}
