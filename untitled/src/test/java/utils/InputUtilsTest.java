package utils;

import com.marsrover.rover.error.BadRequestException;
import com.marsrover.rover.rover.Coordinates;
import com.marsrover.rover.utils.InputUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.marsrover.rover.utils.InputUtils.*;

public class InputUtilsTest {

    @Test
    void should_throwError_when_gridCoordinatesAreMoreThan2Numbers() {
        // Given
        String gridCoordinates = "23 30 50";
        // Then
        commonGridCoordinatesTest(gridCoordinates, GRID_COORDINATES_LONGER_THAN_EXPECTED);
    }

    @Test
    void should_throwError_when_gridCoordinatesAreOnlyNumbers() {
        // Given
        String gridCoordinates = "k 23";
        // Then
        commonGridCoordinatesTest(gridCoordinates, GRID_COORDINATES_MATCHERS);
    }

    @Test
    void should_throwError_when_gridCoordinatesAreGreaterOrLessThanExpected() {
        // Given
        String gridCoordinates = "0 102";
        // Then
        commonGridCoordinatesTest(gridCoordinates, GRID_COORDINATES_REQUIREMENTS);
    }

    @Test
    void should_throwError_when_instructionsAreGreaterThan100Characters() {
        // Given
        String instructions = new String(new char[101]).replace('\0', 'f');
        // Then
        commonInstructionsTest(instructions, INSTRUCTIONS_LENGTH);
    }

    @Test
    void should_throwError_when_instructionsAreNotMatchingLFR() {
        // Given
        String instructions = new String(new char[10]).replace('\0', 'm');
        // Then
        commonInstructionsTest(instructions.toUpperCase(), INSTRUCTIONS_MATCHERS);
    }

    @Test
    void should_thorError_when_roverCoordinatesAreNotMatchingExpected() {
        // Given
        String roverCoordinates = "m k n";
        Coordinates gridCoordinates = Coordinates.builder()
                .coordinateX(50)
                .coordinateY(50)
                .build();
        // Then
        commonRoverCoordinatesTest(roverCoordinates.split(" "), gridCoordinates, ROVER_COORDINATES_MATCHERS);
    }

    @Test
    void should_thorError_when_roverCoordinatesAreOutOfRange() {
        // Given
        String roverCoordinates = "40 25 n";
        Coordinates gridCoordinates = Coordinates.builder()
                .coordinateX(30)
                .coordinateY(25)
                .build();
        // Then
        commonRoverCoordinatesTest(roverCoordinates.toUpperCase().split(" "), gridCoordinates, ROVER_COORDINATES_LENGTH);
    }

    @Test
    void should_returnNumberOfRobots_when_input_is_wellFormed() {
        // Given
        String input = "20 30\n15 10 N\nRFRF\n7 8 S\nLFR";
        // When
        Integer numberOfRobots = InputUtils.getNumberOfRobots(input);
        // Then
        Assert.assertEquals(Optional.of(2).get(), numberOfRobots);
    }

    void commonGridCoordinatesTest(String input, String exception) {
        // When
        BadRequestException badRequestException = Assert.assertThrows(BadRequestException.class, () -> InputUtils.getGridCoordinates(input));
        // Then
        Assert.assertEquals(exception, badRequestException.getMessage());
    }

    void commonInstructionsTest(String input, String exception) {
        // When
        BadRequestException badRequestException = Assert.assertThrows(BadRequestException.class, () -> InputUtils.getInstructions(input));
        // Then
        Assert.assertEquals(exception, badRequestException.getMessage());
    }

    void commonRoverCoordinatesTest(String[] input, Coordinates coordinates, String exception) {
        // When
        BadRequestException badRequestException = Assert.assertThrows(BadRequestException.class, () -> InputUtils.getRoverCoordinates(input, coordinates));
        // Then
        Assert.assertEquals(exception, badRequestException.getMessage());
    }

}
