package com.marsrover.rover.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class Validation {

    Integer numberOfRobots;
    String error;
}
