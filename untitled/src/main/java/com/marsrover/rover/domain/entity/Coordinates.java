package com.marsrover.rover.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Coordinates {

    Integer coordinateX;
    Integer coordinateY;

}
