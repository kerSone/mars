package com.marsrover.rover.domain.dao;

public interface RoverRepository {

    int addResult(String input, String output);

    String getOutput(String input);
}
