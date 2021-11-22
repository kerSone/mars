package com.marsrover.rover.rover;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;
import java.util.Objects;

@Builder
@Getter
@Setter
public class Rover {
    Coordinates coordinates;
    Coordinates gridCordinates;
    Map<Coordinates, String> scent;
    Coordinates dropOffPosition;
    String orientation;
    String instructions;

    public String getFinalPosition(Map<Coordinates, String> receivedScent) {
        scent = receivedScent;
        for (int i = 0; i < instructions.length(); i++) {
            if (coordinates.getCoordinateX() <= gridCordinates.getCoordinateX()
                    && coordinates.getCoordinateY() <= gridCordinates.getCoordinateY()) {
                if (orientation.equals("N")) {
                    if (instructions.charAt(i) == 'F') {
                        if (!scent.isEmpty()) {
                            for (Map.Entry<Coordinates, String> scentCoordinates : scent.entrySet()) {
                                Coordinates coordinatesFromScent = scentCoordinates.getKey();
                                String orientationFromScent = scentCoordinates.getValue();
                                if (Objects.equals(coordinatesFromScent.getCoordinateY(), coordinates.getCoordinateY())
                                        && Objects.equals(coordinatesFromScent.getCoordinateX(), coordinates.getCoordinateX())
                                        && orientationFromScent.equals(orientation)) {
                                    break;
                                }
                                coordinates.setCoordinateY(coordinates.getCoordinateY() + 1);
                            }
                        } else {
                            coordinates.setCoordinateY(coordinates.getCoordinateY() + 1);
                        }
                    } else if (instructions.charAt(i) == 'R') {
                        orientation = "E";
                    } else if (instructions.charAt(i) == 'L') {
                        orientation = "W";
                    }
                } else if (orientation.equals("S")) {
                    if (instructions.charAt(i) == 'F') {
                        if (!scent.isEmpty()) {
                            for (Map.Entry<Coordinates, String> scentCoordinates : scent.entrySet()) {
                                Coordinates coordinatesFromScent = scentCoordinates.getKey();
                                String orientationFromScent = scentCoordinates.getValue();
                                if (Objects.equals(coordinatesFromScent.getCoordinateY(), coordinates.getCoordinateY())
                                        && Objects.equals(coordinatesFromScent.getCoordinateX(), coordinates.getCoordinateX())
                                        && orientationFromScent.equals(orientation)) {
                                    break;
                                }
                                coordinates.setCoordinateY(coordinates.getCoordinateY() - 1);
                            }
                        } else {
                            coordinates.setCoordinateY(coordinates.getCoordinateY() - 1);
                        }
                    } else if (instructions.charAt(i) == 'R') {
                        orientation = "W";
                    } else if (instructions.charAt(i) == 'L') {
                        orientation = "E";
                    }
                } else if (orientation.equals("E")) {
                    if (instructions.charAt(i) == 'F') {
                        if (!scent.isEmpty()) {
                            for (Map.Entry<Coordinates, String> scentCoordinates : scent.entrySet()) {
                                Coordinates coordinatesFromScent = scentCoordinates.getKey();
                                String orientationFromScent = scentCoordinates.getValue();
                                if (Objects.equals(coordinatesFromScent.getCoordinateX(), coordinates.getCoordinateX())
                                        && Objects.equals(coordinatesFromScent.getCoordinateY(), coordinates.getCoordinateY())
                                        && orientationFromScent.equals(orientation)) {
                                    break;
                                }
                                coordinates.setCoordinateX(coordinates.getCoordinateX() + 1);
                            }
                        } else {
                            coordinates.setCoordinateX(coordinates.getCoordinateX() + 1);
                        }
                    } else if (instructions.charAt(i) == 'R') {
                        orientation = "S";
                    } else if (instructions.charAt(i) == 'L') {
                        orientation = "N";
                    }
                } else if (orientation.equals("W")) {
                    if (instructions.charAt(i) == 'F') {
                        if (!scent.isEmpty()) {
                            for (Map.Entry<Coordinates, String> scentCoordinates : scent.entrySet()) {
                                Coordinates coordinatesFromScent = scentCoordinates.getKey();
                                String orientationFromScent = scentCoordinates.getValue();
                                if (Objects.equals(coordinatesFromScent.getCoordinateX(), coordinates.getCoordinateX())
                                        && Objects.equals(coordinatesFromScent.getCoordinateY(), coordinates.getCoordinateY())
                                        && orientationFromScent.equals(orientation)) {
                                    break;
                                }
                                coordinates.setCoordinateX(coordinates.getCoordinateX() - 1);
                            }
                        } else {
                            coordinates.setCoordinateX(coordinates.getCoordinateX() - 1);
                        }
                    } else if (instructions.charAt(i) == 'R') {
                        orientation = "N";
                    } else if (instructions.charAt(i) == 'L') {
                        orientation = "S";
                    }
                }
            } else {
                dropOffPosition = Coordinates.builder().build();
                if (coordinates.getCoordinateX() > gridCordinates.getCoordinateX()) {
                    dropOffPosition.setCoordinateX(coordinates.getCoordinateX() - 1);
                    dropOffPosition.setCoordinateY(coordinates.getCoordinateY());
                } else {
                    dropOffPosition.setCoordinateY(coordinates.getCoordinateY() - 1);
                    dropOffPosition.setCoordinateX(coordinates.getCoordinateX());
                }
                return dropOffPosition.getCoordinateX() + " " + dropOffPosition.getCoordinateY() + " " + orientation + " LOST";
            }
        }
        return coordinates.coordinateX + " " + coordinates.getCoordinateY() + " " + orientation;
    }
}
